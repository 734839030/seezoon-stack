package com.seezoon.admin.framework.error;

import com.seezoon.framework.api.CodeMsg;
import com.seezoon.framework.api.DefaultCodeMsgBundle;

/**
 * admin 端需要标准的错误码，非强制，在开发效率与可维护性之间找平衡，统一有利于国际化及后续监控支持
 *
 *
 * @author hdf
 */
public class AdminCodeMsgBundle extends DefaultCodeMsgBundle {

    public static final CodeMsg UNKOWN_LOGIN = new CodeMsg("80000", "未知登录错误，请联系管理员,%s");
    public static final CodeMsg USERNAME_NOT_FOUND = new CodeMsg("80001", "账户密码错误,连续错误5次将锁定24小时");
    public static final CodeMsg BAD_CREDENTIALS = new CodeMsg("80002", "账户密码错误,连续错误5次将锁定24小时");
    public static final CodeMsg USERNAME_LOCKED = new CodeMsg("80003", "账户已被锁定");
    public static final CodeMsg IP_LOCKED = new CodeMsg("80004", "IP已被锁定");
    public static final CodeMsg DISABLED = new CodeMsg("80005", "账户已被禁用");

}
