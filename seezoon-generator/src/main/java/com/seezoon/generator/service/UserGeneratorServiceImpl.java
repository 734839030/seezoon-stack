package com.seezoon.generator.service;

import java.io.IOException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.seezoon.generator.io.CodeGenerator;
import com.seezoon.generator.io.FileCodeGenerator;
import com.seezoon.generator.plan.TablePlanHandler;
import com.seezoon.generator.plan.UserTablePlanParam;
import com.seezoon.generator.plan.impl.UserTablePlanHandlerImpl;

/**
 * @author hdf
 */
@Service
@Validated
public class UserGeneratorServiceImpl extends AbstractGeneratorService {

    private FileCodeGenerator fileCodeGenerator = new FileCodeGenerator();

    public void generate(@Valid @NotNull UserTablePlanParam userTablePlanParam) throws IOException {
        TablePlanHandler tablePlanHandler = new UserTablePlanHandlerImpl(userTablePlanParam);
        super.generate(userTablePlanParam.getTableName(), tablePlanHandler);
    }

    @Override
    protected CodeGenerator getCodeGenerator() {
        return fileCodeGenerator;
    }
}
