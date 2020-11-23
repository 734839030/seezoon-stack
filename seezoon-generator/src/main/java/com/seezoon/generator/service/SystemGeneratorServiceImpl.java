package com.seezoon.generator.service;

import org.springframework.stereotype.Service;

import com.seezoon.generator.plan.TablePlanHandler;
import com.seezoon.generator.plan.impl.SystemTablePlanHandlerImpl;

/**
 * 系统默认生成方案
 *
 * @author hdf
 */
@Service
public class SystemGeneratorServiceImpl extends AbstractGeneratorService {

    public void generate(String tableName) {
        TablePlanHandler tablePlanHandler = new SystemTablePlanHandlerImpl();
        super.generate(tableName, tablePlanHandler);
    }
}
