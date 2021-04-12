package com.seezoon.generator.plan.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import com.seezoon.generator.constants.InputType;
import com.seezoon.generator.constants.QueryType;
import com.seezoon.generator.constants.db.ColumnKey;
import com.seezoon.generator.constants.db.DefaultColumns;
import com.seezoon.generator.dto.db.DbTable;
import com.seezoon.generator.dto.db.DbTableColumn;
import com.seezoon.generator.plan.*;

/**
 * 用户自定义生成方案
 *
 * @author hdf
 */
public class UserTablePlanHandlerImpl implements TablePlanHandler {

    private UserTablePlanParam userTablePlanParam;

    public UserTablePlanHandlerImpl(UserTablePlanParam userTablePlanParam) {
        this.userTablePlanParam = userTablePlanParam;
    }

    @Override
    public TablePlan generate(DbTable dbTable, List<DbTableColumn> dbTableColumns) {
        TablePlanHandler systemTablePlanHandler = new SystemTablePlanHandlerImpl();
        TablePlan tablePlan = systemTablePlanHandler.generate(dbTable, dbTableColumns);
        // 主基础字段
        tablePlan.setTableName(userTablePlanParam.getTableName());
        tablePlan.setMenuName(userTablePlanParam.getMenuName());
        tablePlan.setModuleName(userTablePlanParam.getModuleName());
        tablePlan.setFunctionName(userTablePlanParam.getFunctionName());
        tablePlan.setTemplateType(userTablePlanParam.getTemplateType());
        tablePlan.setClassName(userTablePlanParam.getClassName());

        // 列属性
        Map<String, ColumnPlan> defaultColumnPlans =
            tablePlan.getColumnPlans().stream().collect(Collectors.toMap(ColumnPlan::getDbColumnName, v -> v));

        for (UserColumnPlanParam userColumnPlanParam : userTablePlanParam.getColumnPlans()) {
            ColumnPlan columnPlan = defaultColumnPlans.get(userColumnPlanParam.getDbColumnName());
            Assert.notNull(columnPlan, String.format("[%s] column not exists", columnPlan.getDbColumnName()));
            columnPlan.setFieldName(userColumnPlanParam.getFieldName());
            columnPlan.setJavaFieldName(userColumnPlanParam.getJavaFieldName());
            columnPlan.setSort(userColumnPlanParam.getSort());
            columnPlan.setInsert(userColumnPlanParam.isInsert());
            columnPlan.setUpdate(userColumnPlanParam.isUpdate());
            columnPlan.setList(userColumnPlanParam.isList());
            columnPlan.setSortable(userColumnPlanParam.isSortable());
            columnPlan.setSearch(userColumnPlanParam.isSearch());
            columnPlan.setQueryType(StringUtils.isNotBlank(userColumnPlanParam.getQueryType())
                ? QueryType.valueOf(userColumnPlanParam.getQueryType()) : QueryType.NONE);
            columnPlan.setInputType(StringUtils.isNotBlank(userColumnPlanParam.getInputType())
                ? InputType.valueOf(userColumnPlanParam.getInputType()) : InputType.NONE);
            columnPlan.setDictType(userColumnPlanParam.getDictType());

        }
        // 处理更新列而引起的生成方案的变更
        this.adjust(tablePlan);
        return tablePlan;
    }

    private void adjust(TablePlan tablePlan) {
        if (null != tablePlan.getColumnPlans()) {
            for (ColumnPlan columnPlan : tablePlan.getColumnPlans()) {

                boolean allowSearchAndListAndSortable =
                    !ArrayUtils.contains(new InputType[] {InputType.RICH_TEXT, InputType.IMAGE, InputType.FILE},
                        columnPlan.getInputType());

                if (!allowSearchAndListAndSortable) {
                    columnPlan.setSearch(false);
                    columnPlan.setList(false);
                    columnPlan.setSortable(false);
                    columnPlan.setQueryType(QueryType.NONE);
                }
                boolean allowDict = ArrayUtils.contains(
                    new InputType[] {InputType.SELECT, InputType.SELECT_MULTIPLE, InputType.RADIO, InputType.CHECKBOX},
                    columnPlan.getInputType());
                if (!allowDict) {
                    columnPlan.setDictType(null);
                }

                if (ColumnKey.PRI.equals(columnPlan.getColumnKey())) {
                    tablePlan.getPkPlan().setJavaFieldName(columnPlan.getJavaFieldName());
                    tablePlan.getPkPlan()
                        .setDefaultJavaPkName(columnPlan.getJavaFieldName().equals(DefaultColumns.id.name()));
                }
                if (!tablePlan.isSortable()) {
                    tablePlan.setSortable(columnPlan.isSortable());
                }
                if (!tablePlan.isHasSearch()) {
                    tablePlan.setHasSearch(columnPlan.isSearch());
                }
                if (!tablePlan.isHasDictWidget()) {
                    tablePlan.setHasDictWidget(ArrayUtils.contains(new InputType[] {InputType.SELECT,
                        InputType.SELECT_MULTIPLE, InputType.RADIO, InputType.CHECKBOX}, columnPlan.getInputType()));
                }
                if (!tablePlan.isHasRichTextWidget()) {
                    tablePlan.setHasRichTextWidget(columnPlan.getInputType().equals(InputType.RICH_TEXT));
                }
                if (!tablePlan.isHasDateWidget()) {
                    tablePlan.setHasDateWidget(columnPlan.getInputType().equals(InputType.DATE)
                        || columnPlan.getInputType().equals(InputType.DATETIME));
                }
                if (!tablePlan.isHasFileUploadWidget()) {
                    tablePlan.setHasFileUploadWidget(columnPlan.getInputType().equals(InputType.FILE));
                }
                if (!tablePlan.isHasImageUploadWidget()) {
                    tablePlan.setHasImageUploadWidget(columnPlan.getInputType().equals(InputType.IMAGE));
                }
            }
            tablePlan.getColumnPlans().sort(null);
        }
    }
}
