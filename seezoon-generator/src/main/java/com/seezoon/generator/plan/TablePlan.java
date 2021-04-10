package com.seezoon.generator.plan;

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.seezoon.generator.constants.InputType;
import com.seezoon.generator.constants.QueryType;
import com.seezoon.generator.constants.db.ColumnKey;
import com.seezoon.generator.constants.db.DefaultColumns;

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
    private List<ColumnPlan> columnPlans;

    /**
     * 是否引入Date DB类型决定
     */
    private boolean importDate;
    /**
     * 是否引入BigDecimal DB 类型决定
     */
    private boolean importBigDecimal;

    /**
     * 字段可排序查询
     */
    private boolean sortable;
    /**
     * 是否有大字段 DB 类型决定
     */
    private boolean hasBlob;
    /**
     * 是否有搜索
     */
    private boolean hasSearch;

    /**
     * 是否有字典组件(下拉，单选，多选)
     */
    private boolean hasDictWidget;

    /**
     * 是否富文本
     */
    private boolean hasRichTextWidget;
    /**
     * 日期控件
     */
    private boolean hasDateWidget;
    /**
     * 是否有文件上传
     */
    private boolean hasFileUploadWidget;
    /**
     * 图片上传
     */
    private boolean hasImageUploadWidget;

    public void adjust() {
        if (null != this.getColumnPlans()) {
            for (ColumnPlan columnPlan : this.getColumnPlans()) {

                boolean allowSearchAndListAndSortable = !ArrayUtils.contains(
                    new String[] {InputType.RICH_TEXT.name(), InputType.IMAGE.name(), InputType.FILE.name()},
                    columnPlan.getInputType());

                if (!allowSearchAndListAndSortable) {
                    columnPlan.setSearch(false);
                    columnPlan.setList(false);
                    columnPlan.setSortable(false);
                    columnPlan.setQueryType(QueryType.NONE);
                }
                boolean allowDict =
                    ArrayUtils.contains(new String[] {InputType.SELECT.name(), InputType.SELECT_MULTIPLE.name(),
                        InputType.RADIO.name(), InputType.CHECKBOX.name()}, columnPlan.getInputType());
                if (!allowDict) {
                    columnPlan.setDictType(null);
                }

                if (ColumnKey.PRI.name().equals(columnPlan.getColumnKey())) {
                    this.getPkPlan().setJavaFieldName(columnPlan.getJavaFieldName());
                    this.getPkPlan()
                        .setDefaultJavaPkName(columnPlan.getJavaFieldName().equals(DefaultColumns.id.name()));
                }
                if (!this.isSortable()) {
                    this.setSortable(columnPlan.isSortable());
                }
                if (!this.isHasSearch()) {
                    this.setHasSearch(columnPlan.isSearch());
                }
                if (!this.hasDictWidget) {
                    this.setHasDictWidget(
                        ArrayUtils.contains(new String[] {InputType.SELECT.name(), InputType.SELECT_MULTIPLE.name(),
                            InputType.RADIO.name(), InputType.CHECKBOX.name()}, columnPlan.getInputType()));
                }
                if (!this.isHasRichTextWidget()) {
                    this.setHasRichTextWidget(columnPlan.getInputType().equals(InputType.RICH_TEXT.name()));
                }
                if (!this.isHasDateWidget()) {
                    this.setHasDateWidget(columnPlan.getInputType().equals(InputType.DATE.name())
                        || columnPlan.getInputType().equals(InputType.DATETIME.name()));
                }
                if (!this.isHasFileUploadWidget()) {
                    this.setHasFileUploadWidget(columnPlan.getInputType().equals(InputType.FILE.name()));
                }
                if (!this.isHasImageUploadWidget()) {
                    this.setHasImageUploadWidget(columnPlan.getInputType().equals(InputType.IMAGE.name()));
                }
            }
            this.columnPlans.sort(null);
        }
    }

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
}
