package com.seezoon.framework.api;

/**
 * 通用响应码及响应消息，子业务模块如果需要规范错误码可以继承该类
 *
 * 不采用枚举的原因是因为，子业务模块都需要自己的定义响应码及响应消息，但枚举无法继承
 *
 * @author hdf
 */
public class DefaultCodeMsgBundle {

    public static final CodeMsg SUCCESS = new CodeMsg("0", "Success");
    public static final CodeMsg FAIL = new CodeMsg("-1", null);
    public static final CodeMsg UNKNOWN = new CodeMsg("90000", "Unregistered Error,%s");
    public static final CodeMsg PARAM_INVALID = new CodeMsg("90001", "Param Invalid,%s");
    public static final CodeMsg PARAM_BIND_ERROR = new CodeMsg("90002", "Param Bind Error,%s");

}
