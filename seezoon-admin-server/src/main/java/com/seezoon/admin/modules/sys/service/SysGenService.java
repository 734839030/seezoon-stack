package com.seezoon.admin.modules.sys.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.seezoon.admin.framework.service.AbstractCrudService;
import com.seezoon.dao.modules.sys.SysGenDao;
import com.seezoon.dao.modules.sys.entity.SysGen;
import com.seezoon.framework.exception.BusinessException;
import com.seezoon.generator.constants.InputType;
import com.seezoon.generator.constants.QueryType;
import com.seezoon.generator.constants.db.ColumnKey;
import com.seezoon.generator.constants.db.DefaultColumns;
import com.seezoon.generator.dao.GeneratorDao;
import com.seezoon.generator.dto.db.DbTable;
import com.seezoon.generator.dto.db.DbTableColumn;
import com.seezoon.generator.plan.*;
import com.seezoon.generator.plan.impl.SystemTablePlanHandlerImpl;

import lombok.RequiredArgsConstructor;

/**
 * 代码生成
 *
 * @author seezoon-generator 2021年3月29日 下午11:27:05
 */
@Service
@RequiredArgsConstructor
public class SysGenService extends AbstractCrudService<SysGenDao, SysGen, Integer> {

    private final GeneratorDao generatorDao;

    @Transactional(readOnly = true)
    public List<String> findTables() {
        List<DbTable> tables = generatorDao.findTable(null);
        return tables.stream().map(DbTable::getName).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TablePlan findDefaultTablePlan(@NotBlank String tableName) {
        DbTable table = generatorDao.findTable(tableName).stream().findFirst()
            .orElseThrow(() -> new BusinessException("%s 不存在", tableName));
        List<DbTableColumn> dbTableColumns = generatorDao.findColumnByTableName(tableName);
        TablePlanHandler systemTablePlanHandler = new SystemTablePlanHandlerImpl();
        TablePlan tablePlan = systemTablePlanHandler.generate(table, dbTableColumns);
        return tablePlan;
    }

    @Transactional(readOnly = true)
    public TablePlan findCustomTablePlan(@NotNull Integer id) {
        SysGen sysGen = this.find(id);
        Assert.notNull(sysGen, "can't get custom table plan id = " + id);
        TablePlan tablePlan = this.findDefaultTablePlan(sysGen.getTableName());
        Assert.notNull(tablePlan, String.format("[%s] table not exists", sysGen.getTableName()));
        // 主基础字段
        tablePlan.setTableName(sysGen.getTableName());
        tablePlan.setMenuName(sysGen.getMenuName());
        tablePlan.setModuleName(sysGen.getModuleName());
        tablePlan.setFunctionName(sysGen.getFunctionName());
        tablePlan.setTemplateType(sysGen.getTemplate());
        tablePlan.setClassName(sysGen.getClassName());

        List<UserColumnPlanParam> userColumnPlanParams =
            JSON.parseArray(sysGen.getColumns(), UserColumnPlanParam.class);

        // 列属性
        Map<String, ColumnPlan> defaultColumnPlans =
            tablePlan.getColumnPlans().stream().collect(Collectors.toMap(ColumnPlan::getDbColumnName, v -> v));

        for (UserColumnPlanParam userColumnPlanParam : userColumnPlanParams) {
            ColumnPlan columnPlan = defaultColumnPlans.get(userColumnPlanParam.getDbColumnName());
            Assert.notNull(columnPlan, String.format("[%s] column not exists", columnPlan.getDbColumnName()));
            if (ColumnKey.PRI.name().equals(columnPlan.getColumnKey())) {
                tablePlan.getPkPlan().setJavaFieldName(userColumnPlanParam.getJavaFieldName());
                tablePlan.getPkPlan()
                    .setDefaultJavaPkName(userColumnPlanParam.getJavaFieldName().equals(DefaultColumns.id.name()));
            }
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

    /**
     * 保存和修改方案
     *
     * @param id
     * @param userTablePlanParam
     * @return
     */
    public int save(Integer id, @NotNull @Valid UserTablePlanParam userTablePlanParam) {
        SysGen sysGen = new SysGen();
        sysGen.setId(id);
        sysGen.setTableName(userTablePlanParam.getTableName());
        sysGen.setMenuName(userTablePlanParam.getMenuName());
        sysGen.setModuleName(userTablePlanParam.getModuleName());
        sysGen.setFunctionName(userTablePlanParam.getFunctionName());
        sysGen.setTemplate(userTablePlanParam.getTemplateType());
        sysGen.setClassName(userTablePlanParam.getClassName());
        sysGen.setColumns(JSON.toJSONString(userTablePlanParam.getColumnPlans()));
        return id == null ? this.save(sysGen) : this.update(sysGen);
    }

}
