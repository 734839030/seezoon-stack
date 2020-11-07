package com.seezoon.framework.api;

import lombok.Getter;
import lombok.Setter;

/**
 * web 层通用返回结果，按照约定，只出现在web层
 *
 * 默认code = '0' 成功 ，code = '-1' 错误
 *
 * @author hdf
 */
@Getter
@Setter
public class Result<T> {

    public final static Result SUCCESS = new Result(DefaultCodeMsgBundle.SUCCESS);

    private String code;
    private String msg;
    private T data;

    private Result(String code, String msg, Object... args) {
        this.code = code;
        this.msg = (null != args ? String.format(msg, args) : msg);
    }

    private Result(CodeMsg codeMsg, Object... args) {
        this(codeMsg.code(), codeMsg.msg(), args);
    }

    public static <T> Result<T> ok(T data) {
        Result<T> result = new Result(DefaultCodeMsgBundle.SUCCESS);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> error(String msg, Object... agrs) {
        Result<T> result = new Result<T>(DefaultCodeMsgBundle.FAIL.code(), msg, agrs);
        return result;
    }

    /**
     * 支持{@link String#format}格式化参数
     *
     * @param code
     * @param msg
     * @param agrs
     * @param <T>
     * @return
     */
    public static <T> Result<T> error(String code, String msg, Object... agrs) {
        Result<T> result = new Result<>(code, msg, agrs);
        return result;
    }

    /**
     * 支持{@link String#format}格式化参数
     *
     * @param codeMsg
     * @param agrs
     *            参数
     * @param <T>
     * @return
     */
    public static <T> Result<T> error(CodeMsg codeMsg, Object... agrs) {
        Result<T> result = new Result<T>(codeMsg, agrs);
        return result;
    }
}
