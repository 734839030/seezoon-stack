package com.seezoon.admin.modules.sys.security;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.seezoon.admin.framework.file.FileService;
import com.seezoon.admin.modules.sys.dto.UserInfo;
import com.seezoon.admin.modules.sys.security.constant.LockType;
import com.seezoon.admin.modules.sys.service.SysUserService;
import com.seezoon.dao.framework.constants.EntityStatus;
import com.seezoon.dao.modules.sys.entity.SysMenu;
import com.seezoon.dao.modules.sys.entity.SysRole;
import com.seezoon.dao.modules.sys.entity.SysUser;
import com.seezoon.framework.utils.IpUtil;

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
    @Autowired
    private FileService fileService;
    @Autowired
    private LoginSecurityService loginSecurityService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        HttpServletRequest request =
            ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        String remoteIp = IpUtil.getRemoteIp(request);
        boolean ipLocked = loginSecurityService.getIpLockStrategy().isLocked(remoteIp);
        if (ipLocked) {
            throw new LockedException(LockType.IP.name());
        }

        if (StringUtils.isBlank(username)) {
            throw new UsernameNotFoundException("username is empty");
        }

        boolean locked = loginSecurityService.getUsernameLockStrategy().isLocked(username);
        if (locked) {
            throw new LockedException(LockType.USERNAME.name());
        }
        SysUser user = sysUserService.findByUsername(username);
        if (null == user) {
            throw new UsernameNotFoundException(username + "  not found");
        }

        if (EntityStatus.INVALID.status() == user.getStatus()) {
            throw new DisabledException(username + " disabled");
        }

        UserInfo userInfo = new UserInfo(user.getId(), user.getDeptId(), user.getUsername(), user.getName());
        userInfo.setDeptName(user.getDeptName());
        userInfo.setPhoto(fileService.getUrl(user.getPhoto()));
        // 角色及权限信息登录成功后放入
        AdminUserDetails adminUserDetails = new AdminUserDetails(userInfo, username, user.getPassword());
        adminUserDetails.setAuthorities(getAuthorities(user.getId()));
        return adminUserDetails;
    }

    /**
     *
     * @return
     */
    private List<GrantedAuthority> getAuthorities(Integer userId) {
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
        return authorities;
    }
}
