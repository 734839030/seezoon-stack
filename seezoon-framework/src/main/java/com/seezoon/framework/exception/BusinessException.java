package com.seezoon.framework.exception;

import com.seezoon.framework.api.CodeMsg;
import com.seezoon.framework.api.DefaultCodeMsgBundle;

/**
 * 业务自定义异常
 *
 * 一般逻辑尽量不使用异常，异常成本较高，可以使用业务逻辑处理，返回错误码
 *
 * 如果业务层次太深，用异常使得结构更合理则可以用
 *
 *
 * @author hdf
 */
public class BusinessException extends RuntimeException {

    private String code;
    private String msg;

    public BusinessException(String code, String msg, Object... args) {
        super(null != args ? String.format(msg, args) : msg);
        this.code = code;
        this.msg = super.getMessage();
    }

    /**
     * 默认错误码
     *
     * @param msg
     */
    public BusinessException(String msg) {
        this(DefaultCodeMsgBundle.FAIL.code(), msg);
    }

    public BusinessException(CodeMsg codeMsg, Object... agrs) {
        this(codeMsg.code(), codeMsg.msg(), agrs);
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
