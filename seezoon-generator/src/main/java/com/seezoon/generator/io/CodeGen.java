package com.seezoon.generator.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import com.seezoon.generator.constants.CodeTemplate;
import com.seezoon.generator.plan.TablePlan;

/**
 * @author hdf
 */
public class CodeGen {

    public static void main(String[] args) {}

    public void gen(TablePlan tablePlan) {
        Arrays.stream(CodeTemplate.values()).forEach((ct) -> {
            String content = FreeMarkerRender.renderTemplate(ct.tplName(), tablePlan);
            try {
                final Path path = Paths.get(
                    "/Users/hdf/Documents/develop/projects/idea-projects/seezoon-stack/seezoon-generator/target/generated-sources",
                    String.format(ct.path(), tablePlan.getModuleName()));
                if (!Files.exists(path)) {
                    Files.createDirectories(path);
                }
                if (!Files.exists(Paths.get(path.toString(), "mapper.xml"))) {
                    Files.createFile(Paths.get(path.toString(), "mapper.xml"));
                }
                Files.writeString(Paths.get(path.toString(), "mapper.xml"), content);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
