package com.seezoon.generator.dto.plan;

import com.seezoon.generator.constants.InputType;
import com.seezoon.generator.constants.QueryType;
import com.seezoon.generator.constants.db.ColumnDataType;
import com.seezoon.generator.constants.db.ColumnExtra;
import com.seezoon.generator.constants.db.ColumnKey;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 代码生成每列的生成方案
 *
 * @author hdf 2018年4月27日
 */
@Getter
@Setter
@Builder
public class ColumnPlan implements Comparable<ColumnPlan> {

    /**
     * DB 列名称
     */
    private String dbColumnName;
    /**
     * 列备注
     */
    private String columnComment;
    /**
     * 主键类型 如PRI(主键) UNI(唯一) MUL(普通索引) {@link ColumnKey}
     */
    private ColumnKey columnKey;
    /**
     * 主键额外说明 如auto_increment
     */
    private ColumnExtra extra;
    /**
     * 列类型 eg:varchar(64)
     */
    private String columnType;
    /**
     * 数据类型eg DB:varchar jdbcType:VARCHAR,javaType:String
     */
    private ColumnDataType dataType;
    /**
     * 字段长度eg:64
     */
    private Integer maxLength;

    // 如下字段接收前端参数设置
    /**
     * 字段名称
     */
    private String javaFieldName;
    /**
     * 是否可空
     */
    private boolean nullable;
    /**
     * 排序
     */
    private Integer sort;
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
     * 查询方式 {@link QueryType}
     */
    private QueryType queryType;
    /**
     * 表单类型 {@link InputType}
     */
    private InputType inputType;
    /**
     * 字典类型
     */
    private String dictType;

    @Override
    public int compareTo(ColumnPlan o) {
        return this.sort - o.getSort();
    }

}
