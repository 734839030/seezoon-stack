package com.seezoon.generator.plan;

import java.util.List;

import com.seezoon.generator.dto.db.DbTable;
import com.seezoon.generator.dto.db.DbTableColumn;

/**
 *
 * @author hdf
 */
public interface TablePlanHandler {

    public TablePlan generate(DbTable dbTable, List<DbTableColumn> dbTableColumns);
}
