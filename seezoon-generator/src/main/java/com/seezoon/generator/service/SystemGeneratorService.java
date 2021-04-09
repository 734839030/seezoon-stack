package com.seezoon.generator.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;

import com.seezoon.generator.dto.db.DbTable;
import com.seezoon.generator.dto.db.DbTableColumn;
import com.seezoon.generator.io.FileCodeGenerator;
import com.seezoon.generator.plan.TablePlan;
import com.seezoon.generator.plan.TablePlanHandler;
import com.seezoon.generator.plan.impl.SystemTablePlanHandlerImpl;

/**
 * 系统默认生成方案
 *
 * @author hdf
 */
@Service
public class SystemGeneratorService extends AbstractGeneratorService {

    public void generate(String... tableNames) throws IOException {
        TablePlanHandler tablePlanHandler = new SystemTablePlanHandlerImpl();
        FileCodeGenerator fileCodeGenerator = new FileCodeGenerator();

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
        fileCodeGenerator.generate(tablePlans.toArray(new TablePlan[tablePlans.size()]));
    }
}
