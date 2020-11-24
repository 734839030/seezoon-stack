package com.seezoon.generator.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.seezoon.generator.io.CodeGenerator;
import com.seezoon.generator.io.FileCodeGenerator;
import com.seezoon.generator.plan.TablePlanHandler;
import com.seezoon.generator.plan.impl.SystemTablePlanHandlerImpl;

/**
 * 系统默认生成方案
 *
 * @author hdf
 */
@Service
public class SystemGeneratorServiceImpl extends AbstractGeneratorService {

    TablePlanHandler tablePlanHandler = new SystemTablePlanHandlerImpl();
    private FileCodeGenerator fileCodeGenerator = new FileCodeGenerator();

    public void generate(String tableName) throws IOException {
        super.generate(tableName, tablePlanHandler);
    }

    @Override
    protected CodeGenerator getCodeGenerator() {
        return fileCodeGenerator;
    }
}
