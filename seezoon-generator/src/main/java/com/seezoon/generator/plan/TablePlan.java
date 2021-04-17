package com.seezoon.generator.plan;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.seezoon.generator.constants.InputType;

import lombok.Getter;
import lombok.Setter;

/**
 * 根据表获得的生成方案
 *
 * @author hdf
 */
@Getter
@Setter
public class TablePlan {
    // 如果不需要请置位空字符串
    public static final String DEFAULT_TABLE_ALIAS = "t";
    public static final String DEFAULT_TABLE_ALIAS_PREFIX =
        StringUtils.isNotBlank(DEFAULT_TABLE_ALIAS) ? DEFAULT_TABLE_ALIAS + "." : "";
    /**
     * 表名
     */
    private String tableName;
    /**
     * 主键类型
     */
    private PkPlan pkPlan;
    /**
     * 菜单名，默认为DB表的备注
     */
    private String menuName;

    /**
     * 模块名，默认为表的前缀
     */
    private String moduleName;
    /**
     * 功能模块，默认为去掉前缀
     */
    private String functionName;
    /**
     * 生成模板,保留,方便未来生成树结构等
     */
    private Integer templateType;

    /**
     * 类名，默认表名转大驼峰
     */
    private String className;

    /**
     * 字段方案
     */
    private List<ColumnPlan> columnPlans = Collections.emptyList();

    /**
     * 为了让freemark读取到{@code defaultTableAlias}
     *
     * @return
     */
    public String getDefaultTableAlias() {
        return DEFAULT_TABLE_ALIAS;
    }

    /**
     * 为了让freemark读取到{@code defaultTableAliasPrefix}
     *
     * @return
     */
    public String getDefaultTableAliasPrefix() {
        return DEFAULT_TABLE_ALIAS_PREFIX;
    }

    public boolean isImportDate() {
        return this.getColumnPlans().stream()
            .anyMatch(columnPlan -> Date.class.getSimpleName().equals(columnPlan.getDataType().javaType()));
    }

    public boolean isImportBigDecimal() {
        return this.getColumnPlans().stream()
            .anyMatch(columnPlan -> BigDecimal.class.getSimpleName().equals(columnPlan.getDataType().javaType()));
    }

    public boolean isHasSearch() {
        return this.getColumnPlans().stream().anyMatch(columnPlan -> columnPlan.isSearch());
    }

    /**
     * 字段可排序查询
     *
     * @return
     */
    public boolean isSortable() {
        return this.getColumnPlans().stream().anyMatch(columnPlan -> columnPlan.isSortable());
    }

    public boolean isHasDateWidget() {
        return this.getColumnPlans().stream().anyMatch(columnPlan -> columnPlan.getInputType().equals(InputType.DATE)
            || columnPlan.getInputType().equals(InputType.DATETIME));
    }

    public boolean isHasDictWidget() {
        return this.getColumnPlans().stream()
            .anyMatch(columnPlan -> ArrayUtils.contains(
                new InputType[] {InputType.SELECT, InputType.SELECT_MULTIPLE, InputType.RADIO, InputType.CHECKBOX},
                columnPlan.getInputType()));
    }

    public boolean isHasBlob() {
        return this.getColumnPlans().stream().anyMatch(columnPlan -> columnPlan.isBlobType());
    }

    public boolean isHasRichTextWidget() {
        return this.getColumnPlans().stream()
            .anyMatch(columnPlan -> columnPlan.getInputType().equals(InputType.RICH_TEXT));
    }

    public boolean isHasFileUploadWidget() {
        return this.getColumnPlans().stream().anyMatch(columnPlan -> columnPlan.getInputType().equals(InputType.FILE));
    }

    public boolean isHasImageUploadWidget() {
        return this.getColumnPlans().stream().anyMatch(columnPlan -> columnPlan.getInputType().equals(InputType.IMAGE));
    }
}
