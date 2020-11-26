package com.seezoon.generator.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;

import com.seezoon.generator.constants.CodeTemplate;
import com.seezoon.generator.plan.TablePlan;

import lombok.extern.slf4j.Slf4j;

/**
 * 文件的方式生成
 * <p>
 * 生成目录target/seezoon-sources/
 *
 * @author hdf
 */
@Slf4j
public class FileCodeGenerator implements CodeGenerator {

    @Override
    public void generate(TablePlan... tablePlans) throws IOException {
        // 获取根目录
        Path generatedFolderPath = Paths.get(this.getGeneratedSourcesFolder());
        this.emptyGeneratedFolder(generatedFolderPath);
        for (TablePlan tablePlan : tablePlans) {
            Arrays.stream(CodeTemplate.values()).forEach((ct) -> {
                this.createSourceFile(tablePlan, generatedFolderPath, ct);
            });
        }
    }

    private void createSourceFile(TablePlan tablePlan, Path generatedFolderPath, CodeTemplate ct) {
        try {
            Path fileParentPath =
                Paths.get(generatedFolderPath.toString(), String.format(ct.path(), tablePlan.getModuleName()));
            if (!Files.exists(fileParentPath)) {
                Files.createDirectories(fileParentPath);
            }
            Path filePath = Path.of(fileParentPath.toString(), String.format(ct.fileName(), tablePlan.getClassName()));
            Files.createFile(filePath);
            String content = FreeMarkerRender.renderTemplate(ct.tplName(), tablePlan);
            Files.writeString(filePath, content);
            log.info("success generated source files in {}", GENERATED_SOURCES_FOLDER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void emptyGeneratedFolder(Path generatedFolderPath) throws IOException {
        if (Files.exists(generatedFolderPath)) {
            FileUtils.deleteDirectory(generatedFolderPath.toFile());
        }
        Files.createDirectories(generatedFolderPath);
    }

    /**
     * 获取生成目录 target/GENERATED_SOURCES_FOLDER
     *
     * @return
     */
    public String getGeneratedSourcesFolder() {
        // 获取更目录
        String classRoot = FileCodeGenerator.class.getClassLoader().getResource("").getPath();
        // 去掉末尾的分隔符号
        classRoot = classRoot.substring(0, classRoot.length() - 1);
        String generatedSourcesParent = classRoot.substring(0, classRoot.lastIndexOf(File.separator) + 1);
        return generatedSourcesParent + GENERATED_SOURCES_FOLDER;
    }
}
