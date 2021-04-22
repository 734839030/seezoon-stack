package com.seezoon.admin.framework.context;

import com.seezoon.admin.modules.sys.dto.UserInfo;
import com.seezoon.admin.modules.sys.security.SecurityUtils;

/**
 * 用户信息，可以服用spring security 的用户上下文
 *
 *
 */
public class UserContext {

    public static Integer getUserId(){
        UserInfo user = SecurityUtils.getUser();
        return null != user ? user.getUserId() : SecurityUtils.ANONYMOUS_USER_ID;
    }
}
