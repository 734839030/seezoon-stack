package com.seezoon.user.framework.context;

import com.seezoon.user.modules.security.SecurityUtils;
import com.seezoon.user.modules.security.dto.UserInfo;

/**
 * 用户信息，可以服用spring security 的用户上下文
 *
 *
 */
public class UserContext {

    public static Integer getUserId() {
        UserInfo user = SecurityUtils.getUser();
        return null != user ? user.getUserId() : SecurityUtils.ANONYMOUS_USER_ID;
    }
}
