package com.seezoon.admin.modules.sys.security.data;

// @ApiModelProperty(value = "数据范围:0:全部，1：本部门，2：本部门及以下，3.下级部门，4.指定部门，5.本人", required = true)
public enum DataScope {
    ALL(0, "全部"),

    SELF_DEPT(1, "本部门"),

    SELF_DEPT_AND_BELOW(2, "本部门及以下"),

    DEPT_AND_BELOW(3, "下级部门"),

    ASSIGN_DEPT(4, "指定部门"),

    SELF(5, "本人"),

    ;

    private int code;
    private String name;

    DataScope(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int code() {
        return code;
    }
}
