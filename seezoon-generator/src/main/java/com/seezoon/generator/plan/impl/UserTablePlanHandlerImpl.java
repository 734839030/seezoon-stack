package com.seezoon.generator.plan.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import com.seezoon.generator.constants.InputType;
import com.seezoon.generator.constants.QueryType;
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
        tablePlan.adjust();
        return tablePlan;
    }
}
