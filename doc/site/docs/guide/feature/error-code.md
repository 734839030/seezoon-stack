# 错误码

正常响应和异常时候都应该返回响应码，在出现错误时候使用错误码，方便快速定位问题。这里为了方便，普通提示性错误码用-1。

## 错误码结构

其中msg ，可以使用String.format支持的占位符。

```java
package com.seezoon.framework.api;

/**
 * 错误码及响应信息结构
 *
 *
 * @author hdf
 */
public class CodeMsg {

    private String code;
    private String msg;

    public CodeMsg(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String code() {
        return code;
    }

    public String msg() {
        return msg;
    }
}

```

::: tip
Result 和BusinessException 都支持传入该结构。
:::

## 错误码举例

> 如果对错误码需要严格细分，可以自己扩展。

```java
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
```

