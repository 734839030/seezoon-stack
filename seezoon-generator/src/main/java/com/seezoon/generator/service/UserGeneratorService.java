package com.seezoon.generator.service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;

import com.seezoon.generator.dto.db.DbTable;
import com.seezoon.generator.dto.db.DbTableColumn;
import com.seezoon.generator.io.ZipStreamCodeGenerator;
import com.seezoon.generator.plan.TablePlan;
import com.seezoon.generator.plan.TablePlanHandler;
import com.seezoon.generator.plan.UserTablePlanParam;
import com.seezoon.generator.plan.impl.SystemTablePlanHandlerImpl;
import com.seezoon.generator.plan.impl.UserTablePlanHandlerImpl;

import lombok.RequiredArgsConstructor;

/**
 * @author hdf
 */
@Service
@RequiredArgsConstructor
public class UserGeneratorService extends AbstractGeneratorService {

    public List<String> findTables() {
        List<DbTable> tables = generatorDao.findTable(null);
        return tables.stream().map(DbTable::getName).collect(Collectors.toList());
    }

    /**
     * 获取默认生成方案
     *
     * @param tableName
     * @return
     */
    public TablePlan findDefaultTablePlan(@NotBlank String tableName) {
        DbTable dbTable = super.findTable(tableName);
        List<DbTableColumn> dbTableColumns = super.findDbTableColumns(tableName);
        TablePlanHandler systemTablePlanHandler = new SystemTablePlanHandlerImpl();
        TablePlan tablePlan = systemTablePlanHandler.generate(dbTable, dbTableColumns);
        return tablePlan;
    }

    /**
     * 用户自定义方案 合并默认方案
     *
     * @param userTablePlanParam
     * @return
     */
    public TablePlan customTablePlan(@Valid @NotNull UserTablePlanParam userTablePlanParam) {
        DbTable dbTable = super.findTable(userTablePlanParam.getTableName());
        List<DbTableColumn> dbTableColumns = super.findDbTableColumns(userTablePlanParam.getTableName());
        TablePlanHandler userTablePlanHandler = new UserTablePlanHandlerImpl(userTablePlanParam);
        TablePlan tablePlan = userTablePlanHandler.generate(dbTable, dbTableColumns);
        return tablePlan;
    }

    public void generate(@Valid @NotNull UserTablePlanParam userTablePlanParam, OutputStream outputStream)
        throws IOException {
        TablePlanHandler tablePlanHandler = new UserTablePlanHandlerImpl(userTablePlanParam);

        super.generate(new ZipStreamCodeGenerator(outputStream), tablePlanHandler, userTablePlanParam.getTableName());
    }
}
