package com.seezoon.user.modules.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;

import com.seezoon.user.modules.security.dto.UserInfo;

public class SecurityUtils {

    public static final Integer SUPER_ADMIN_USER_ID = 1;
    public static final Integer ANONYMOUS_USER_ID = -1;

    public static Integer getUserId() {
        UserInfo user = getUser();
        Assert.notNull(user, "thread context don't contain any user");
        return user.getUserId();
    }

    public static UserInfo getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 匿名也是Authenticated = true
        if (null != authentication && authentication.isAuthenticated()) {
            // 如果是匿名，返回的是个字符串
            Object principal = authentication.getPrincipal();
            if (principal instanceof CustomUserDetails) {
                CustomUserDetails customUserDetails = (CustomUserDetails)principal;
                return customUserDetails.getUserInfo();
            }
        }
        return null;
    }
}