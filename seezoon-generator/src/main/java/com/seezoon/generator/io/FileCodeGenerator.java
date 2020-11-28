package com.seezoon.generator.io;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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

    private static final String ZIP_NAME = "all-sources-in-one.zip";

    @Override
    public void generate(TablePlan... tablePlans) throws IOException {
        // 获取根目录
        Path generatedFolderPath = this.getGeneratedSourcesFolder();
        this.emptyGeneratedFolder(generatedFolderPath);
        for (TablePlan tablePlan : tablePlans) {
            Arrays.stream(CodeTemplate.values()).forEach((ct) -> {
                this.createSourceFile(tablePlan, generatedFolderPath, ct);
            });
        }
        try (OutputStream fos = Files.newOutputStream(generatedFolderPath.resolve(ZIP_NAME));
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            ZipOutputStream zipOutputStream = new ZipOutputStream(bos)) {
            // 生成zip包
            Files.walk(generatedFolderPath).filter(p -> !Files.isDirectory(p) && !p.endsWith(ZIP_NAME)).forEach((f) -> {
                try (InputStream fis = Files.newInputStream(f);
                    BufferedInputStream bins = new BufferedInputStream(fis)) {
                    String relativePath = generatedFolderPath.relativize(f).toString();
                    zipOutputStream.putNextEntry(new ZipEntry(relativePath));
                    // 写入文件
                    byte[] buff = new byte[(int)FileUtils.ONE_KB];
                    int len = 0;
                    while (-1 != (len = bins.read(buff))) {
                        zipOutputStream.write(buff, 0, len);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    private void createSourceFile(TablePlan tablePlan, Path generatedFolderPath, CodeTemplate ct) {
        try {
            // 得到生成文件的完成目录
            Path fileParentPath = generatedFolderPath.resolve(String.format(ct.path(), tablePlan.getModuleName()));
            if (!Files.exists(fileParentPath)) {
                Files.createDirectories(fileParentPath);
            }
            // 得到生成文件路径
            Path filePath = fileParentPath.resolve(String.format(ct.fileName(), tablePlan.getClassName()));
            Files.createFile(filePath);
            String content = FreeMarkerRender.renderTemplate(ct.tplName(), tablePlan);
            Files.writeString(filePath, content);
            log.info("success generated source files {}", filePath.toString());
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
}
