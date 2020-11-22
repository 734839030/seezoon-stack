package com.seezoon.generator.constants;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.Assert;

/**
 * mysql 类型与mybatis jdbcType 映射
 *
 * @author hdf 2018年4月27日
 */
public class GenTypeMapping {

    private static Map<String, String> dbMybatisTypeMapping = new HashMap<String, String>();
    private static Map<String, String> dbJavaTypeMapping = new HashMap<String, String>();

    /**
     * db mybatis 常用映射
     */
    static {
        dbMybatisTypeMapping.put("varchar", "VARCHAR");
        dbMybatisTypeMapping.put("char", "CHAR");
        dbMybatisTypeMapping.put("tinyint", "TINYINT");
        dbMybatisTypeMapping.put("int", "INTEGER");
        dbMybatisTypeMapping.put("integer", "INTEGER");
        dbMybatisTypeMapping.put("double", "DOUBLE");
        dbMybatisTypeMapping.put("float", "REAL");
        dbMybatisTypeMapping.put("datetime", "TIMESTAMP");
        dbMybatisTypeMapping.put("date", "DATE");
        dbMybatisTypeMapping.put("time", "TIME");
        dbMybatisTypeMapping.put("timestamp", "TIMESTAMP");
        dbMybatisTypeMapping.put("decimal", "DECIMAL");
        dbMybatisTypeMapping.put("numeric", "DECIMAL");
        dbMybatisTypeMapping.put("text", "LONGVARCHAR");
        dbMybatisTypeMapping.put("tinytext", "VARCHAR");
        dbMybatisTypeMapping.put("longtext", "LONGVARCHAR");
        dbMybatisTypeMapping.put("mediumtext", "LONGVARCHAR");
        dbMybatisTypeMapping.put("bigint", "BIGINT");
    }

    /**
     * db java 常用映射
     */
    static {
        dbJavaTypeMapping.put("varchar", "String");
        dbJavaTypeMapping.put("char", "String");
        dbJavaTypeMapping.put("tinyint", "Short");
        dbJavaTypeMapping.put("int", "Integer");
        dbJavaTypeMapping.put("integer", "Integer");
        dbJavaTypeMapping.put("double", "Double");
        dbJavaTypeMapping.put("float", "Float");
        // 导入java.util.Date;
        dbJavaTypeMapping.put("datetime", "Date");
        dbJavaTypeMapping.put("date", "Date");
        dbJavaTypeMapping.put("time", "Date");
        dbJavaTypeMapping.put("timestamp", "Date");
        // 导入java.math.BigDecimal;
        dbJavaTypeMapping.put("decimal", "BigDecimal");
        dbJavaTypeMapping.put("numeric", "BigDecimal");
        dbJavaTypeMapping.put("text", "String");
        dbJavaTypeMapping.put("tinytext", "String");
        dbJavaTypeMapping.put("longtext", "String");
        dbJavaTypeMapping.put("mediumtext", "String");
        dbJavaTypeMapping.put("bigint", "Long");

    }

    /**
     * 通过DB 类型获得 mybatis类型
     *
     * @param dbType
     * @return
     */
    public static String getDbMybatisMapping(String dbType) {
        Assert.hasLength(dbType, "dbType 为空");
        String mapping = dbMybatisTypeMapping.get(dbType.toLowerCase());
        Assert.notNull(mapping, dbType + "无对应mybatis 映射，请补全");
        return mapping;
    }

    /**
     * 通过DB 类型获得 java类型
     *
     * @param dbType
     * @return
     */
    public static String getDbJavaMapping(String dbType) {
        Assert.hasLength(dbType, "dbType 为空");
        String mapping = dbJavaTypeMapping.get(dbType.toLowerCase());
        Assert.notNull(mapping, dbType + "无对应mybatis 映射，请补全");
        return mapping;
    }
}
