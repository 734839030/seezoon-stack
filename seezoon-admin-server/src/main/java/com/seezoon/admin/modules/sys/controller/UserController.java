package com.seezoon.admin.modules.sys.controller;

import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seezoon.admin.modules.sys.dto.UserInfo;
import com.seezoon.admin.modules.sys.dto.UserInfoVo;
import com.seezoon.admin.modules.sys.dto.vue.RouteMeta;
import com.seezoon.admin.modules.sys.dto.vue.VueRouteMenu;
import com.seezoon.admin.modules.sys.security.SecurityUtils;
import com.seezoon.admin.modules.sys.security.UserService;
import com.seezoon.dao.modules.sys.entity.SysMenu;
import com.seezoon.framework.api.Result;
import com.seezoon.framework.utils.TreeHelper;
import com.seezoon.framework.web.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Api(tags = "个人用户信息")
@RestController
@RequestMapping("/sys/user")
@RequiredArgsConstructor
public class UserController extends BaseController {

    private final UserService userService;

    @ApiOperation(value = "获取用户信息")
    @GetMapping("/getInfo")
    public Result<UserInfoVo> getUserInfo() {
        UserInfo user = SecurityUtils.getUser();
        // 角色
        Set<String> roles =
            userService.findRolesByUserId(user.getUserId()).stream().map(v -> v.getCode()).collect(Collectors.toSet());

        List<SysMenu> menus = userService.findMenusByUserId(user.getUserId());
        // 权限
        Set<String> permissions = menus.stream().filter(v -> StringUtils.isNotBlank(v.getPermission()))
            .map(v -> StringUtils.trim(v.getPermission())).collect(Collectors.toSet());

        UserInfoVo userInfoVo = new UserInfoVo(user.getUserId(), user.getUsername(), user.getName(), roles, permissions,
            this.getRoutes(menus));

        return Result.ok(userInfoVo);
    }

    private List<VueRouteMenu> getRoutes(List<SysMenu> menus) {
        Map<Integer, List<SysMenu>> parentIdGroup = menus.stream().filter(v -> SysMenu.MENU_TYPE_BUTTON != v.getType())
            .collect(Collectors.groupingBy(sysMenu -> sysMenu.getParentId()));
        // 先找到根节点
        List<SysMenu> sysMenus = parentIdGroup.get(TreeHelper.DEFAULT_PARENT_ID);
        if (null == sysMenus || sysMenus.isEmpty()) {
            return Collections.emptyList();
        }
        return this.genRoutes(sysMenus, parentIdGroup);
    }

    private List<VueRouteMenu> genRoutes(List<SysMenu> sysMenus, Map<Integer, List<SysMenu>> parentIdGroup) {
        List<VueRouteMenu> vueRouteMenus = new ArrayList<>();
        sysMenus.forEach(menu -> {
            VueRouteMenu route = new VueRouteMenu();
            route.setName(menu.getUrl());
            route.setMeta(new RouteMeta(menu.getName(), menu.getIcon()));
            if (menu.getType() == SysMenu.MENU_TYPE_DIRECTORY) {
                route.setPath(menu.getUrl());
                route.setComponent(VueRouteMenu.COMPONENT_LAYOUT);
            } else if (menu.getType() == SysMenu.MENU_TYPE_BUTTON) {
                if (null != menu.getUrl() && !menu.getUrl().startsWith("https://")
                    && !menu.getUrl().startsWith("http://")) {
                    route.setComponent(menu.getUrl());
                } else { // 外部链接
                    route.setComponent(VueRouteMenu.COMPONENT_IFRAME);
                    if (SysMenu.TARGET_MAIN.equals(menu.getTarget())) {// 内部特殊结构,外部前端自动处理
                        route.setPath("/" + menu.getId());
                        route.getMeta().setFrameSrc(menu.getUrl());
                    }
                }
            }

            // 处理子路由
            List<SysMenu> children = parentIdGroup.get(menu.getId());
            if (null != children && !children.isEmpty()) {
                route.setChildren(this.genRoutes(children, parentIdGroup));
            }
            vueRouteMenus.add(route);
        });
        return vueRouteMenus;
    }
}
