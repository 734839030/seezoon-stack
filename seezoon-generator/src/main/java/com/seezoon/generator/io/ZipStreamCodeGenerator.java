package com.seezoon.generator.io;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.util.Assert;

import com.seezoon.generator.constants.CodeTemplate;
import com.seezoon.generator.plan.TablePlan;

/**
 * 适合下载生成文件的生成器,传入输出流
 *
 * @author hdf
 */
public class ZipStreamCodeGenerator implements CodeGenerator {

    private OutputStream outputStream;

    public ZipStreamCodeGenerator(OutputStream outputStream) {
        Assert.notNull(outputStream, "outputStream must not null");
        this.outputStream = outputStream;
    }

    @Override
    public void generate(TablePlan... tablePlans) throws IOException {
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream)) {
            for (TablePlan tablePlan : tablePlans) {
                Arrays.stream(CodeTemplate.values()).forEach((ct) -> {
                    // 得到生成文件路径
                    String content = FreeMarkerRender.renderTemplate(ct.tplName(), tablePlan);
                    String path = FreeMarkerRender.renderStringTemplate(ct.path(), tablePlan);
                    String name = FreeMarkerRender.renderStringTemplate(ct.fileName(), tablePlan);
                    System.out.println(Path.of(path, name).toString());
                    addNextEntry(zipOutputStream, Path.of(path, name).toString(), content);
                });
            }
        }
    }

    private void addNextEntry(ZipOutputStream zipOutputStream, String zipEntryName, String content) {
        try {
            zipOutputStream.putNextEntry(new ZipEntry(zipEntryName));
            IOUtils.write(content, zipOutputStream, StandardCharsets.UTF_8);
            zipOutputStream.closeEntry();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
