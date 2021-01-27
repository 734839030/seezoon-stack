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
    public static final CodeMsg SAVE_ERROR = new CodeMsg("80001", "保存失败，影响条数:%d,请联系管理员");
    public static final CodeMsg UPDATE_ERROR = new CodeMsg("80002", "更新失败，影响条数:%d,请联系管理员");
    public static final CodeMsg DELETE_ERROR = new CodeMsg("80003", "删除失败，影响条数:%d,请联系管理员");
    public static final CodeMsg COMMON_DATA_CHANGE_ERROR = new CodeMsg("80004", "处理失败，影响条数:%d,请联系管理员");

}
