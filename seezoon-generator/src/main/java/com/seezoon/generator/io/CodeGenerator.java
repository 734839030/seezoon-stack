package com.seezoon.generator.io;

import java.io.IOException;
import java.nio.file.Path;

import org.springframework.boot.system.ApplicationHome;

import com.seezoon.generator.plan.TablePlan;

/**
 * 代码生成
 *
 * @author hdf
 */
public interface CodeGenerator {

    public static final String GENERATED_SOURCES_FOLDER = "seezoon-generated";

    public void generate(TablePlan... tablePlans) throws IOException;

    /**
     * 开发环境则为target,jar环境则为jar所在目录
     *
     * @return
     */
    default public Path getGeneratedSourcesFolder() {
        ApplicationHome ah = new ApplicationHome(CodeGenerator.class);
        Path docStorePath = ah.getSource().getParentFile().toPath();
        return docStorePath.resolve(GENERATED_SOURCES_FOLDER);
    }
}
