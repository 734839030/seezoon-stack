package com.seezoon.generator.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

import com.seezoon.generator.dao.GeneratorDao;
import com.seezoon.generator.dto.db.DbTable;
import com.seezoon.generator.dto.db.DbTableColumn;
import com.seezoon.generator.io.CodeGenerator;
import com.seezoon.generator.plan.TablePlan;
import com.seezoon.generator.plan.TablePlanHandler;

/**
 * 生成服务
 *
 * @author hdf
 */
@Validated
public abstract class AbstractGeneratorService {

    @Autowired
    protected GeneratorDao generatorDao;

    /**
     * 生成，{@code ArrayUtils.isEmpty(tableNames)} 时候生成全部
     *
     * @param tablePlanHandler
     * @param tableNames
     * @throws IOException
     */
    protected void generate(CodeGenerator codeGenerator, TablePlanHandler tablePlanHandler, String... tableNames)
        throws IOException {
        List<DbTable> allDbTables = new ArrayList<>();
        if (ArrayUtils.isEmpty(tableNames)) {
            allDbTables = generatorDao.findTable(null);
        } else {
            for (String tableName : tableNames) {
                allDbTables.add(this.findTable(tableName));
            }
        }
        List<TablePlan> tablePlans = new ArrayList<>();
        allDbTables.forEach((dbTable) -> {
            List<DbTableColumn> dbTableColumns = findDbTableColumns(dbTable.getName());
            TablePlan tablePlan = tablePlanHandler.generate(dbTable, dbTableColumns);
            tablePlans.add(tablePlan);
        });
        codeGenerator.generate(tablePlans.toArray(new TablePlan[tablePlans.size()]));
    }

    public List<DbTableColumn> findDbTableColumns(@NotBlank String tableName) {
        List<DbTableColumn> dbTableColumns = generatorDao.findColumnByTableName(tableName);
        return dbTableColumns;
    }

    public DbTable findTable(@NotBlank String tableName) {
        DbTable dbTable = generatorDao.findTable(tableName).stream().findFirst()
            .orElseThrow(() -> new RuntimeException(String.format("can't find tableName:%s", tableName)));
        return dbTable;
    }
}
