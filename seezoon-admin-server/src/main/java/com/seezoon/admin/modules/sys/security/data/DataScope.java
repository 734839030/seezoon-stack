package com.seezoon.admin.modules.sys.security.data;

/**
 * 数据范围,还可以实现其他的数据权限自己扩展,采用通用数据权限，建议不要弄的太复杂，对数据权限高的还得在业务层处理。
 */
public enum DataScope {
    ALL(0, "全部"),

    SELF_DEPT(10, "本部门"),

    SELF_DEPT_AND_BELOW(20, "本部门及以下"),

    SELF(30, "本人"),

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
