package com.seezoon.generator.io;

import java.io.IOException;

import com.seezoon.generator.plan.TablePlan;

/**
 * 代码生成
 *
 * @author hdf
 */
public interface CodeGenerator {
    public void generate(TablePlan tablePlan) throws IOException;
}
