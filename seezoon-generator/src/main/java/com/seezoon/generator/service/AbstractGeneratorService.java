package com.seezoon.generator.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

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
public abstract class AbstractGeneratorService {

    @Autowired
    private GeneratorDao generatorDao;

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
                List<DbTable> dbTable = generatorDao.findTable(tableName);
                Assert.notEmpty(dbTable, String.format("can't find tableName:%s", tableName));
                allDbTables.addAll(dbTable);
            }
        }
        List<TablePlan> tablePlans = new ArrayList<>();
        allDbTables.forEach((dbTable) -> {
            List<DbTableColumn> dbTableColumns = generatorDao.findColumnByTableName(dbTable.getName());
            TablePlan tablePlan = tablePlanHandler.generate(dbTable, dbTableColumns);
            tablePlans.add(tablePlan);
        });
        codeGenerator.generate(tablePlans.toArray(new TablePlan[tablePlans.size()]));
    }
}
