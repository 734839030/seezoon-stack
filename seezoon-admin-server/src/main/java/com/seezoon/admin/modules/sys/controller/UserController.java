package com.seezoon.admin.modules.sys.controller;

import java.util.*;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.CaseUtils;
import org.springframework.web.bind.annotation.*;

import com.seezoon.admin.modules.sys.dto.UserAo;
import com.seezoon.admin.modules.sys.dto.UserInfo;
import com.seezoon.admin.modules.sys.dto.UserResourcesVo;
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
    @GetMapping("/info")
    public Result<UserInfo> getUserInfo() {
        UserInfo user = userService.userInfo(SecurityUtils.getUserId());
        return Result.ok(user);
    }

    @ApiOperation(value = "获取用户信息")
    @PostMapping("/save_user_info")
    public Result saveUserInfo(@Valid @RequestBody UserAo userAo) {
        userService.saveUserInfo(SecurityUtils.getUserId(), userAo);
        return Result.SUCCESS;
    }

    @ApiOperation(value = "更新密码")
    @PostMapping("/update_password")
    public Result updatePassword(@RequestParam @NotEmpty @Size(min = 6, max = 50) String oldPassword,
        @RequestParam @NotEmpty @Size(min = 6, max = 50) String newPassword) {
        boolean result = userService.updatePassword(SecurityUtils.getUserId(), oldPassword, newPassword);
        if (!result) {
            return Result.error("原密码错误");
        }
        return Result.SUCCESS;
    }

    @ApiOperation(value = "获取资源")
    @GetMapping("/get_resources")
    public Result<UserResourcesVo> getResources() {
        UserInfo user = SecurityUtils.getUser();
        // 角色
        Set<String> roles =
            userService.findRolesByUserId(user.getUserId()).stream().map(v -> v.getCode()).collect(Collectors.toSet());

        List<SysMenu> menus = userService.findMenusByUserId(user.getUserId());
        // 权限
        Set<String> permissions = menus.stream().filter(v -> StringUtils.isNotBlank(v.getPermission()))
            .map(v -> StringUtils.trim(v.getPermission())).collect(Collectors.toSet());

        UserResourcesVo userResourcesVo = new UserResourcesVo(roles, permissions, this.getRoutes(menus));

        return Result.ok(userResourcesVo);
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
            route.setMeta(new RouteMeta(menu.getName(), menu.getIcon()));
            if (menu.getType() == SysMenu.MENU_TYPE_DIRECTORY) {
                route.setComponent(VueRouteMenu.COMPONENT_LAYOUT);
            } else if (menu.getType() == SysMenu.MENU_TYPE_MENU) {
                if (null != menu.getUrl() && !menu.getUrl().startsWith("https://")
                    && !menu.getUrl().startsWith("http://")) {
                    route.setComponent(menu.getUrl() + "/index");
                    route.setPath(menu.getUrl());
                } else { // 外部链接
                    route.setComponent(VueRouteMenu.COMPONENT_IFRAME);
                    if (SysMenu.TARGET_MAIN.equals(menu.getTarget())) {// 内部特殊结构,外部前端自动处理
                        route.setPath("/" + menu.getId());
                        route.getMeta().setFrameSrc(menu.getUrl());
                    } else {
                        route.setPath(menu.getUrl());
                    }
                }
            }
            // 名字和组件名字对应才可以keepAlive
            if (StringUtils.isNotEmpty(menu.getUrl())) {
                String[] menuSplit = StringUtils.split(menu.getUrl(), "/");
                if (menuSplit.length > 0) {
                    String name = "";
                    for (String s : menuSplit) {
                        name += CaseUtils.toCamelCase(s, true, '_', '-');
                    }
                    route.setName(name);
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
