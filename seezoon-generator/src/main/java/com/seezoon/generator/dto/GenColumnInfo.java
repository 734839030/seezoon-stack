package com.seezoon.generator.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 代码生成每列的信息
 *
 * @author hdf 2018年4月27日
 */
@Getter
@Setter
public class GenColumnInfo implements Comparable<GenColumnInfo> {

    /**
     * DB 列名称
     */
    private String dbColumnName;
    /**
     * 列备注
     */
    private String columnComment;
    /**
     * 主键类型 如PRI
     */
    private String columnKey;
    /**
     * 主键额外说明 如auto_increment
     */
    private String extra;
    /**
     * 列类型 eg:varchar(64)
     */
    private String columnType;
    /**
     * 数据类型eg:varchar
     */
    private String dataType;
    /**
     * 字段长度eg:64
     */
    private Long maxLength;
    /**
     * 对应的java类型
     */
    private String javaType;
    /**
     * 对应的mysbtis JDBC类型
     */
    private String jdbcType;
    /**
     * 字段名称
     */
    private String javaFieldName;
    /**
     * 是否可空
     */
    private Boolean nullable;
    /**
     * 是否可插入
     */
    private boolean insert;
    /**
     * 是否可更新
     */
    private boolean update;
    /**
     * 是否列表展示
     */
    private boolean list;
    /**
     * 是否列表排序
     */
    private boolean sortable;
    /**
     * 是否搜索条件
     */
    private boolean search;
    /**
     * 查询方式，eg >= = <= like等
     */
    private String queryType;
    /**
     * 表单类型
     */
    private String inputType;
    /**
     * 字典类型
     */
    private String dictType;
    /**
     * 排序
     */
    private Integer sort;

    @Override
    public int compareTo(GenColumnInfo o) {
        return this.sort - o.getSort();
    }

}
