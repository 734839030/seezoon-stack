package com.seezoon.admin.modules.sys.security;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.seezoon.admin.modules.sys.service.SysUserService;
import com.seezoon.dao.framework.constants.EntityStatus;
import com.seezoon.dao.modules.sys.entity.SysMenu;
import com.seezoon.dao.modules.sys.entity.SysRole;
import com.seezoon.dao.modules.sys.entity.SysUser;

/**
 * 用户加载逻辑
 *
 * @author hdf
 */
public class AdminUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;
    @Autowired
    private SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isBlank(username)) {
            throw new UsernameNotFoundException("username is empty");
        }
        SysUser user = sysUserService.findByUsername(username);
        if (null == user) {
            throw new UsernameNotFoundException(username + "  not found");
        }

        if (EntityStatus.INVALID.status() == user.getStatus()) {
            throw new DisabledException(username + " disabled");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        // 角色处理
        List<SysRole> userRoles = userService.findRolesByUserId(user.getId());
        userRoles.stream().filter(v -> StringUtils.isNotBlank(v.getCode())).forEach(v -> {
            authorities.add(new UserGrantedAuthority(v.getCode(), true));
        });
        List<SysMenu> userMenus = userService.findMenusByUserId(user.getId());
        userMenus.stream().filter(v -> StringUtils.isNotBlank(v.getPermission())).forEach(v -> {
            authorities.add(new UserGrantedAuthority(v.getPermission()));
        });
        AdminUserDetails adminUserDetails =
            new AdminUserDetails(user.getId(), user.getDeptId(), username, user.getPassword(), false, authorities);
        return adminUserDetails;
    }
}
