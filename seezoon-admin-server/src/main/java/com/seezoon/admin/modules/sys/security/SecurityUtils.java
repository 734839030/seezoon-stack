package com.seezoon.admin.modules.sys.security;

import java.util.Objects;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.seezoon.admin.modules.sys.dto.UserInfo;
import org.springframework.util.Assert;

public class SecurityUtils {

    public static final Integer SUPER_ADMIN_USER_ID = 1;
    public static final Integer ANONYMOUS_USER_ID = - 1;

    public static Integer getUserId() {
        UserInfo user = getUser();
        Assert.notNull(user,"thread context don't contain any user");
        return user.getUserId();
    }

    public static UserInfo getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null != authentication) {
            Object principal = authentication.getPrincipal();
            AdminUserDetails adminUserDetails = (AdminUserDetails)principal;
            return adminUserDetails.getUserInfo();
        }
        return null;
    }

    public static boolean isSuperAdmin(Integer userId) {
        return Objects.equals(userId, SUPER_ADMIN_USER_ID);
    }

    public static boolean isSuperAdmin() {
        return isSuperAdmin(getUserId());
    }

}