package com.seezoon.generator.service;

import java.io.IOException;
import java.util.ArrayList;
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

    /**
     * 生成，tableName = null 时候生成全部
     *
     * @param tableName
     * @param tablePlanHandler
     * @throws IOException
     */
    protected void generate(String tableName, TablePlanHandler tablePlanHandler) throws IOException {
        List<DbTable> dbTables = generatorDao.findTable(tableName);
        Assert.notEmpty(dbTables, String.format("can't find tableName:%s", tableName));
        List<TablePlan> tablePlans = new ArrayList<>();
        dbTables.forEach((dbTable) -> {
            List<DbTableColumn> dbTableColumns = generatorDao.findColumnByTableName(dbTable.getName());
            TablePlan tablePlan = tablePlanHandler.generate(dbTable, dbTableColumns);
            tablePlans.add(tablePlan);
        });
        this.getCodeGenerator().generate(tablePlans.toArray(new TablePlan[tablePlans.size()]));
    }

    protected abstract CodeGenerator getCodeGenerator();
}
