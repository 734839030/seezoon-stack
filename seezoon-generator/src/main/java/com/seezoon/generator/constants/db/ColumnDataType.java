package com.seezoon.generator.constants.db;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.Assert;

/**
 * 默认都用包装类型
 *
 * 常用数据库字段类型->mybatis 类型-> java 类型，这里生成避免sql过长，不加入mybatis type
 * <p>
 * mybatis 类型可以不用，mybatis自动推导的，这里预留，在datetime和timestamp 场景会有区别，日常开发遇到特殊情况，自行修改sql即可
 *
 * @author hdf
 */
public enum ColumnDataType {
    // @formatter:off
    VARCHAR("varchar", "VARCHAR", String.class.getSimpleName()),
    CHAR("char", "CHAR", String.class.getSimpleName()),
    TINYINT("tinyint", "TINYINT", Integer.class.getSimpleName()),
    INT("int", "INTEGER", Integer.class.getSimpleName()),
    INTEGER("integer", "INTEGER", Integer.class.getSimpleName()),
    BIGINT("bigint", "BIGINT", Long.class.getSimpleName()),
    DOUBLE("double", "DOUBLE", Double.class.getSimpleName()),
    FLOAT("float", "REAL", Float.class.getSimpleName()),
    // 导入时间
    DATETIME("datetime", "TIMESTAMP", Date.class.getSimpleName()),
    DATE("date", "DATE", Date.class.getSimpleName()),
    TIME("time", "TIME", Date.class.getSimpleName()),
    TIMESTAMP("timestamp", "TIMESTAMP", Date.class.getSimpleName()),
    // 导入BigDecimal
    DECIMAL("decimal", "DECIMAL", BigDecimal.class.getSimpleName()),
    NUMERIC("numeric", "DECIMAL", BigDecimal.class.getSimpleName()),
    TEXT("text", "LONGVARCHAR", String.class.getSimpleName()),
    TINYTEXT("tinytext", "VARCHAR", String.class.getSimpleName()),
    MEDIUMTEXT("mediumtext", "LONGVARCHAR", String.class.getSimpleName()),
    LONGTEXT("longtext", "LONGVARCHAR", String.class.getSimpleName()),
    ;
    // @formatter:on

    private static final Map<String, ColumnDataType> COLUMN_DATA_TYPES = new HashMap<>();

    static {
        Arrays.stream(ColumnDataType.values()).forEach((v) -> {
            COLUMN_DATA_TYPES.put(v.dbType, v);
        });
    }

    private String dbType;
    private String jdbcType;
    private String javaType;

    ColumnDataType(String dbType, String mybatisType, String javaType) {
        this.dbType = dbType;
        this.jdbcType = mybatisType;
        this.javaType = javaType;
    }

    public static ColumnDataType parse(String dbType) {
        ColumnDataType columnDataType = COLUMN_DATA_TYPES.get(dbType);
        Assert.isTrue(null != columnDataType,
            String.format("dbType[%s] not support,pls supplement enum ColumnDataType", dbType));
        return columnDataType;
    }

    public String dbType() {
        return dbType;
    }

    public String jdbcType() {
        return jdbcType;
    }

    public String javaType() {
        return javaType;
    }

}
