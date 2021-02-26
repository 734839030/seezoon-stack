package com.seezoon.admin.modules.sys.security;

import java.util.Objects;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static final Integer SUPER_ADMIN_USER_ID = 0;

    public static Integer getUserId() {
        return getUser().getUserId();
    }

    public static AdminUserDetails getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null != authentication) {
            Object principal = authentication.getPrincipal();
            AdminUserDetails adminUserDetails = (AdminUserDetails)principal;
            return adminUserDetails;
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