package com.seezoon.generator.plan.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.seezoon.generator.constants.InputType;
import com.seezoon.generator.constants.QueryType;
import com.seezoon.generator.constants.TemplateType;
import com.seezoon.generator.constants.db.DefaultColumns;
import com.seezoon.generator.dto.db.DbTable;
import com.seezoon.generator.dto.db.DbTableColumn;
import com.seezoon.generator.plan.ColumnPlan;
import com.seezoon.generator.plan.TablePlan;
import com.seezoon.generator.plan.TablePlanHandler;
import com.seezoon.generator.plan.UserTablePlanParam;

/**
 * 用户自定义生成方案
 *
 * @author hdf
 */
public class UserTablePlanHandlerImpl implements TablePlanHandler {

    private UserTablePlanParam userTablePlanParam;
    private TablePlanHandler systemTablePlanHandler = new SystemTablePlanHandlerImpl();

    public UserTablePlanHandlerImpl(UserTablePlanParam userTablePlanParam) {
        this.userTablePlanParam = userTablePlanParam;
    }

    @Override
    public TablePlan generate(DbTable dbTable, List<DbTableColumn> dbTableColumns) {
        TablePlan tablePlan = systemTablePlanHandler.generate(dbTable, dbTableColumns);
        if (null != userTablePlanParam) {
            tablePlan.setMenuName(userTablePlanParam.getMenuName());
            tablePlan.setModuleName(userTablePlanParam.getModuleName());
            tablePlan.setFunctionName(userTablePlanParam.getFunctionName());
            tablePlan.setTemplateType(TemplateType.valueOf(userTablePlanParam.getTemplateType()));
            tablePlan.setClassName(userTablePlanParam.getClassName());
            // 默认方案会自动判断是否search
            tablePlan.setHasSearch(false);
            // 默认方法会对有所以的非默认字段排序
            tablePlan.setSortable(false);
            Map<String, ColumnPlan> columnPlanMapping =
                tablePlan.getColumnPlans().stream().collect(Collectors.toMap(ColumnPlan::getDbColumnName, v -> v));
            // 主键字段名改写
            ColumnPlan pkColumnPlan = columnPlanMapping.get(tablePlan.getPkPlan().getDbColumnName());
            tablePlan.getPkPlan().setJavaFieldName(pkColumnPlan.getJavaFieldName());
            tablePlan.getPkPlan()
                .setDefaultJavaPkName(DefaultColumns.id.name().equals(pkColumnPlan.getJavaFieldName()));
            if (!CollectionUtils.isEmpty(userTablePlanParam.getUserColumnPlanParams())) {
                userTablePlanParam.getUserColumnPlanParams().forEach((u) -> {
                    ColumnPlan columnPlan = columnPlanMapping.get(u.getDbColumnName());
                    Assert.notNull(columnPlan, String.format("can't find DbColumnName[%s]", u.getDbColumnName()));
                    columnPlan.setFieldName(u.getFieldName());
                    columnPlan.setSort(u.getSort());
                    columnPlan.setInsert(u.isInsert());
                    columnPlan.setUpdate(u.isUpdate());
                    columnPlan.setList(u.isList());
                    columnPlan.setSortable(u.isSortable());
                    columnPlan.setSearch(u.isSearch());
                    columnPlan.setQueryType(QueryType.valueOf(u.getQueryType()));
                    columnPlan.setInputType(InputType.parse(u.getInputType()));
                    columnPlan.setDictType(u.getDictType());

                    if (InputType.RICHTEXT.equals(u.getInputType()) && !tablePlan.isHasRichTextWidget()) {
                        tablePlan.setHasRichTextWidget(true);
                    }
                    if (InputType.DATE.equals(u.getInputType()) && !tablePlan.isHasDateWidget()) {
                        tablePlan.setHasDateWidget(true);
                    }
                    if (InputType.FILE.equals(u.getInputType()) && !tablePlan.isHasFileUploadWidget()) {
                        tablePlan.setHasFileUploadWidget(true);
                    }
                    if (InputType.IMAGE.equals(u.getInputType()) && tablePlan.isHasImageUploadWidget()) {
                        tablePlan.setHasImageUploadWidget(true);
                    }

                    if (u.isSearch() && !tablePlan.isHasSearch()) {
                        tablePlan.setHasSearch(true);
                    }
                    if (u.isSortable() && !tablePlan.isSortable()) {
                        tablePlan.setSortable(true);
                    }
                });
            }
        }
        return tablePlan;
    }
}
