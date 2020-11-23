package com.seezoon.generator.plan;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.seezoon.generator.constants.InputType;
import com.seezoon.generator.constants.QueryType;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户列字段自定义计划
 *
 * @author hdf
 */
@Getter
@Setter
public class UserColumnPlanParam {

    /**
     * 前端隐藏提交
     */
    @NotEmpty
    @Size(min = 1, max = 50)
    private String dbColumnName;
    /**
     * 字段名
     */
    @NotEmpty
    @Size(min = 1, max = 50)
    private String fieldName;

    @NotEmpty
    @Size(min = 1, max = 50)
    private String javaFieldName;
    /**
     * 排序
     */
    @NotNull
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
    private String queryType;
    /**
     * 表单类型 {@link InputType}
     */
    private String inputType;
    /**
     * 字典类型
     */
    @Size(max = 32)
    private String dictType;

}
