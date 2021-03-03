package com.seezoon.admin.modules.sys.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.seezoon.admin.framework.file.FileService;
import com.seezoon.admin.modules.sys.dto.UserInfo;
import com.seezoon.admin.modules.sys.service.SysUserService;
import com.seezoon.dao.framework.constants.EntityStatus;
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
    @Autowired
    private FileService fileService;

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

        UserInfo userInfo = new UserInfo(user.getId(), user.getDeptId(), user.getUsername(), user.getName());
        userInfo.setDeptName(user.getDeptName());
        userInfo.setPhoto(fileService.getUrl(user.getPhoto()));
        // 角色及权限信息登录成功后放入
        AdminUserDetails adminUserDetails = new AdminUserDetails(userInfo, username, user.getPassword(), false);
        return adminUserDetails;
    }
}
