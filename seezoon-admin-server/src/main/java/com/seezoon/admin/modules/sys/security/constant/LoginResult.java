package com.seezoon.admin.modules.sys.security.constant;

/**
 * @author hdf
 */
public class LoginResult {
    /**
     * 登录未匹配用户时候使用
     */
    public static final int DEFAULT_USER_ID = -1;

    /**
     * 登录成功
     */
    public static final int SUCCESS = 0;
    /**
     * 账号不存在
     */
    public static final int USERNAME_NOT_FOUND = 10;
    /**
     * 密码错误
     */
    public static final int PASSWD_ERROR = 20;

    /**
     * 禁用
     */
    public static final int DISABLED = 30;

    /**
     * 账户被锁定
     */
    public static final int USERNAME_LOCKED = 40;
    /**
     * IP 被锁定
     */
    public static final int IP_LOCKED = 45;
    /**
     * 未知失败
     */
    public static final int UNKOWN = 50;
}
