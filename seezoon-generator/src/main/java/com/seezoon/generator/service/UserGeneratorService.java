package com.seezoon.generator.service;

import java.io.IOException;
import java.io.OutputStream;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.seezoon.generator.io.ZipStreamCodeGenerator;
import com.seezoon.generator.plan.TablePlanHandler;
import com.seezoon.generator.plan.UserTablePlanParam;
import com.seezoon.generator.plan.impl.UserTablePlanHandlerImpl;

/**
 * @author hdf
 */
@Service
@Validated
public class UserGeneratorService extends AbstractGeneratorService {

    public void generate(@Valid @NotNull UserTablePlanParam userTablePlanParam, OutputStream outputStream)
        throws IOException {
        TablePlanHandler tablePlanHandler = new UserTablePlanHandlerImpl(userTablePlanParam);

        super.generate(new ZipStreamCodeGenerator(outputStream), tablePlanHandler, userTablePlanParam.getTableName());
    }
}
