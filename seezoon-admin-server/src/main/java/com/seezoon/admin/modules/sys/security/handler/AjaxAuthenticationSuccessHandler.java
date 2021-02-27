package com.seezoon.admin.modules.sys.security.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.seezoon.admin.modules.sys.security.AdminUserDetails;
import com.seezoon.admin.modules.sys.security.AdminUserDetailsServiceImpl;
import com.seezoon.admin.modules.sys.security.UserGrantedAuthority;
import com.seezoon.admin.modules.sys.security.UserService;
import com.seezoon.dao.modules.sys.entity.SysMenu;
import com.seezoon.dao.modules.sys.entity.SysRole;
import com.seezoon.framework.api.Result;
import com.seezoon.framework.web.respone.AbstractJsonResponeHandler;

/**
 * 前后端分离场景下的成功处理
 *
 * @author hdf
 */
public class AjaxAuthenticationSuccessHandler extends AbstractJsonResponeHandler
    implements AuthenticationSuccessHandler {

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException {
        setAuthorities(authentication);
        super.sendRespone(response, Result.SUCCESS);
    }

    /**
     * 登录成功后再放入权限,也可以在{@link AdminUserDetailsServiceImpl}中放，不过如果恶意刷库会导致内存占用和DB IO
     *
     * @param authentication
     */
    private void setAuthorities(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        AdminUserDetails adminUserDetails = (AdminUserDetails)principal;
        Integer userId = adminUserDetails.getUserInfo().getUserId();
        List<GrantedAuthority> authorities = new ArrayList<>();
        // 角色处理
        List<SysRole> userRoles = userService.findRolesByUserId(userId);
        userRoles.stream().filter(v -> StringUtils.isNotBlank(v.getCode())).forEach(v -> {
            authorities.add(new UserGrantedAuthority(v.getCode(), true));
        });
        List<SysMenu> userMenus = userService.findMenusByUserId(userId);
        userMenus.stream().filter(v -> StringUtils.isNotBlank(v.getPermission())).forEach(v -> {
            authorities.add(new UserGrantedAuthority(v.getPermission()));
        });
        // 整个流程结束后spring security 会报认证对象保存到对应存储
        adminUserDetails.setAuthorities(authorities);
    }

}
