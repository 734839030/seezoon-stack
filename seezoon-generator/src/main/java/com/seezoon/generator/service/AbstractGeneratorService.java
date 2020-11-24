package com.seezoon.generator.service;

import java.io.IOException;
import java.util.List;

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

    protected void generate(String tableName, TablePlanHandler tablePlanHandler) throws IOException {
        Assert.hasText(tableName, "tableName must be not empty");
        List<DbTable> dbTables = generatorDao.findTable(tableName);
        Assert.notEmpty(dbTables, String.format("can't find tableName:%s", tableName));
        DbTable dbTable = dbTables.get(0);
        List<DbTableColumn> dbTableColumns = generatorDao.findColumnByTableName(tableName);
        TablePlan tablePlan = tablePlanHandler.generatePlan(dbTable, dbTableColumns);
        this.getCodeGenerator().generate(tablePlan);
    }

    protected abstract CodeGenerator getCodeGenerator();
}
