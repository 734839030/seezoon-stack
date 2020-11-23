package com.seezoon.generator.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.seezoon.generator.plan.TablePlanHandler;
import com.seezoon.generator.plan.UserTablePlanParam;
import com.seezoon.generator.plan.impl.UserTablePlanHandlerImpl;

/**
 * @author hdf
 */
@Service
@Validated
public class UserGeneratorServiceImpl extends AbstractGeneratorService {

    public void generate(@Valid @NotNull UserTablePlanParam userTablePlanParam) {
        TablePlanHandler tablePlanHandler = new UserTablePlanHandlerImpl(userTablePlanParam);
        super.generate(userTablePlanParam.getTableName(), tablePlanHandler);
    }
}
