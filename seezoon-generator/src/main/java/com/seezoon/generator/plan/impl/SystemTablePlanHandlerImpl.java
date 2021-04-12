package com.seezoon.generator.plan.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.CaseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seezoon.generator.constants.InputType;
import com.seezoon.generator.constants.QueryType;
import com.seezoon.generator.constants.TemplateType;
import com.seezoon.generator.constants.db.ColumnDataType;
import com.seezoon.generator.constants.db.ColumnExtra;
import com.seezoon.generator.constants.db.ColumnKey;
import com.seezoon.generator.constants.db.DefaultColumns;
import com.seezoon.generator.dto.db.DbTable;
import com.seezoon.generator.dto.db.DbTableColumn;
import com.seezoon.generator.plan.ColumnPlan;
import com.seezoon.generator.plan.PkPlan;
import com.seezoon.generator.plan.TablePlan;
import com.seezoon.generator.plan.TablePlanHandler;

/**
 * 系统默认生成方案
 *
 * @author hdf
 */
public class SystemTablePlanHandlerImpl implements TablePlanHandler {

    private static final Logger logger = LoggerFactory.getLogger(SystemTablePlanHandlerImpl.class);
    /**
     * DB 及表字段的分隔符
     */
    private static final String DB_DELIMITER = "_";
    private static final String[] DEFAULT_COLUMNS =
        {"id", "status", "create_by", "create_time", "update_by", "update_time", "remarks"};

    @Override
    public TablePlan generate(DbTable dbTable, List<DbTableColumn> dbTableColumns) {
        TablePlan tablePlan = new TablePlan();
        tablePlan.setTableName(dbTable.getName());
        tablePlan.setMenuName(dbTable.getComment());
        // 放入默认的模块名和功能名
        List<String> moduleAndFuntion = extractModuleAndFuntion(dbTable.getName());
        tablePlan.setModuleName(moduleAndFuntion.get(0));
        tablePlan.setFunctionName(moduleAndFuntion.get(1));
        tablePlan.setTemplateType(TemplateType.CRUD.ordinal());
        tablePlan.setClassName(CaseUtils.toCamelCase(dbTable.getName(), true, DB_DELIMITER.toCharArray()));
        tablePlan.setColumnPlans(this.createColumnPlan(tablePlan, dbTableColumns));
        return tablePlan;
    }

    private List<ColumnPlan> createColumnPlan(TablePlan tablePlan, List<DbTableColumn> dbTableColumns) {
        List<ColumnPlan> columnPlans = new ArrayList<>();
        dbTableColumns.forEach((v) -> {
            // @formatter:off
            ColumnPlan columnPlan = ColumnPlan.builder()
                    .dbColumnName(v.getName())
                    .fieldName(v.getComment())
                    .columnKey(StringUtils.isNotEmpty(v.getColumnKey()) ? ColumnKey.valueOf(v.getColumnKey())
                            : ColumnKey.NONE)
                    .extra(StringUtils.isNotEmpty(v.getExtra()) ? ColumnExtra.valueOf(v.getExtra()) : ColumnExtra.none)
                    .columnType(v.getColumnType())
                    .dataType(ColumnDataType.parse(v.getDataType()))
                    .maxLength(v.getMaxlength())
                    .javaFieldName(CaseUtils.toCamelCase(v.getName(), false, DB_DELIMITER.toCharArray()))
                    .nullable(v.getNullable())
                    .sort(v.getSort())
                    .queryType(QueryType.NONE)
                    .sortable(false)
                    .insert(true)
                    .update(true)
                    .list(true)
                    .inputType(InputType.NONE)
                    .build();
            // @formatter:on
            // 是否String 类型
            columnPlan.setStringType(columnPlan.getDataType().javaType().equals(String.class.getSimpleName()));
            columnPlan.setInsert(true);
            columnPlan.setUpdate(true);
            columnPlan.setList(true);
            columnPlan.setDefaultField(ArrayUtils.contains(DEFAULT_COLUMNS, columnPlan.getDbColumnName()));

            // 针对默认字段的处理
            if (columnPlan.isDefaultField()) {
                if (DefaultColumns.id.name().equals(columnPlan.getDbColumnName())) {
                    columnPlan.setInputType(InputType.HIDDEN);
                    columnPlan.setList(false);
                    columnPlan.setInsert(false);
                    columnPlan.setUpdate(false);
                } else if (DefaultColumns.status.name().equals(columnPlan.getDbColumnName())) {
                    columnPlan.setInputType(InputType.RADIO);
                } else if (DefaultColumns.create_time.name().equals(columnPlan.getDbColumnName())) {
                    columnPlan.setSortable(true);
                    columnPlan.setUpdate(false);
                    columnPlan.setInputType(InputType.DATETIME);
                } else if (DefaultColumns.update_time.name().equals(columnPlan.getDbColumnName())) {
                    columnPlan.setList(false);
                    columnPlan.setInputType(InputType.DATETIME);
                } else if (DefaultColumns.create_by.name().equals(columnPlan.getDbColumnName())) {
                    columnPlan.setInputType(InputType.INTEGRAL_NUMBER);
                    columnPlan.setList(false);
                    columnPlan.setUpdate(false);
                } else if (DefaultColumns.update_by.name().equals(columnPlan.getDbColumnName())) {
                    columnPlan.setInputType(InputType.INTEGRAL_NUMBER);
                    columnPlan.setList(false);
                } else if (DefaultColumns.remarks.name().equals(columnPlan.getDbColumnName())) {
                    columnPlan.setInputType(InputType.TEXTAREA);
                    columnPlan.setList(false);
                }
            } else {
                // 默认文本框
                if (String.class.getSimpleName().equals(columnPlan.getDataType().javaType())) {
                    columnPlan.setInputType(InputType.TEXT);
                }
                // 时间框
                if (Date.class.getSimpleName().equals(columnPlan.getDataType().javaType())) {
                    tablePlan.setImportDate(true);
                    if (ColumnDataType.DATE.dbType().equals(columnPlan.getDataType().dbType())) {
                        columnPlan.setInputType(InputType.DATE);
                    } else {
                        columnPlan.setInputType(InputType.DATETIME);
                    }
                }

                // jdbcType = LONGVARCHAR的为大文本
                if (ColumnDataType.TEXT.jdbcType().equals(columnPlan.getDataType().jdbcType())) {
                    columnPlan.setInputType(InputType.TEXTAREA);
                    // 列表默认也不展示
                    columnPlan.setList(false);
                    columnPlan.setBlobType(true);
                    tablePlan.setHasBlob(true);
                }

                // 数值
                if (ArrayUtils.contains(new String[] {Integer.class.getSimpleName(), Long.class.getSimpleName(),
                    Short.class.getSimpleName()}, columnPlan.getDataType().javaType())) {
                    columnPlan.setInputType(InputType.INTEGRAL_NUMBER);
                } else if (ArrayUtils.contains(new String[] {Float.class.getSimpleName(), Double.class.getSimpleName()},
                    columnPlan.getDataType().javaType())) {
                    columnPlan.setInputType(InputType.DECIMAL);
                } else if (BigDecimal.class.getSimpleName().equals(columnPlan.getDataType().javaType())) {
                    columnPlan.setInputType(InputType.DECIMAL);
                    tablePlan.setImportBigDecimal(true);
                }

                // 有索引的
                if ((ColumnKey.MUL.equals(columnPlan.getColumnKey())
                    || ColumnKey.UNI.equals(columnPlan.getColumnKey()))) {
                    columnPlan.setSearch(true);
                    columnPlan.setSortable(true);
                    columnPlan.setInputType(InputType.TEXT);
                    columnPlan.setQueryType(QueryType.EQUAL);
                    tablePlan.setHasSearch(true);
                }
            }

            // 主键
            if (columnPlan.getColumnKey().equals(ColumnKey.PRI)) {
                if (null != tablePlan.getPkPlan()) {
                    logger.warn("table[{}] must have only one primary key,otherwise generator select first primary key",
                        tablePlan.getTableName());
                } else {
                    columnPlan.setInputType(InputType.HIDDEN);
                    columnPlan.setList(false);
                    tablePlan.setPkPlan(new PkPlan(columnPlan.getDbColumnName(), columnPlan.getJavaFieldName(),
                        columnPlan.getDataType(), DefaultColumns.id.name().equals(columnPlan.getJavaFieldName()),
                        ColumnExtra.auto_increment.equals(columnPlan.getExtra())));
                    // 自增不可插入
                    columnPlan.setInsert(!tablePlan.getPkPlan().isAutoIncrement());
                    // 主键也不可更新
                    columnPlan.setUpdate(false);
                }
            }
            columnPlans.add(columnPlan);
        });
        tablePlan.setSortable(columnPlans.stream().anyMatch(columnPlan -> columnPlan.isSortable()));
        // 一定要有主键
        if (null == tablePlan.getPkPlan()) {
            throw new IllegalArgumentException(
                String.format("table[%s] must have primary key", tablePlan.getTableName()));
        }
        return columnPlans;
    }

    /**
     * 通过表名提取模块名和功能名，按{@link SystemTablePlanHandlerImpl#DB_DELIMITER} 拆分为2个，只拆第一个分隔符
     *
     * @param tableName
     *            not null
     * @return
     */
    private List<String> extractModuleAndFuntion(String tableName) {
        String[] splitedTableName = tableName.split(DB_DELIMITER, 2);
        return List.of(splitedTableName[0], splitedTableName.length == 2 ? splitedTableName[1] : splitedTableName[0]);
    }
}
