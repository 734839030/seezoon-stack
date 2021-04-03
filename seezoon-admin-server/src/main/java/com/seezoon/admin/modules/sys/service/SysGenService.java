package com.seezoon.admin.modules.sys.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seezoon.admin.framework.service.AbstractCrudService;
import com.seezoon.dao.modules.sys.SysGenDao;
import com.seezoon.dao.modules.sys.entity.SysGen;
import com.seezoon.framework.exception.BusinessException;
import com.seezoon.generator.dao.GeneratorDao;
import com.seezoon.generator.dto.db.DbTable;
import com.seezoon.generator.dto.db.DbTableColumn;
import com.seezoon.generator.plan.TablePlan;
import com.seezoon.generator.plan.TablePlanHandler;
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

}
