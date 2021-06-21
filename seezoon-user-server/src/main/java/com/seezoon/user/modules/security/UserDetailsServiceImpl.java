package com.seezoon.user.modules.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.seezoon.dao.framework.constants.EntityStatus;
import com.seezoon.user.modules.security.dto.UserInfo;

/**
 * 用户加载逻辑
 *
 * @author hdf
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (StringUtils.isBlank(username)) {
            throw new UsernameNotFoundException("username is empty");
        }

        UserInfo user = new UserInfo(null);
        if (null == user) {
            throw new UsernameNotFoundException(username + "  not found");
        }

        if (EntityStatus.INVALID.status() == 0) {
            throw new DisabledException(username + " disabled");
        }

        UserInfo userInfo = new UserInfo(user.getUserId());
        userInfo.setPhotoUrl(userInfo.getPhotoUrl());
        CustomUserDetails userDetails = new CustomUserDetails(userInfo, username, null);
        return userDetails;
    }

}
