package com.seezoon.framework.component.file.handler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import com.seezoon.framework.exception.BusinessException;
import com.seezoon.framework.properties.SeezoonProperties;

/**
 * 本地文件存储处理
 *
 * @author hdf
 */
public class LocalFileHandler implements FileHandler {

    private SeezoonProperties.FileProperties fileProperties;

    public LocalFileHandler(SeezoonProperties.FileProperties fileProperties) {
        Assert.hasText(fileProperties.getLocal().getDirectory(),
            "FileProperties.LocalProperties directory must not be empty");
        Assert.hasText(fileProperties.getUrlPrefix(), "FileProperties urlPrefix must not be empty");
        this.fileProperties = fileProperties;
    }

    /**
     * 上传文件
     * <p>
     * 目的地目录不存在会自动创建,文件存在则会覆盖
     *
     * @param relativePath
     * @param in
     *            会被关闭{@link FileUtils#copyInputStreamToFile(InputStream, File)}
     * @throws IOException
     */
    @Override
    public void upload(String relativePath, InputStream in) throws IOException {
        Assert.hasLength(relativePath, "relativePath must not be empty");
        Assert.notNull(in, "inputStream must not be null");
        Path storePath = Path.of(fileProperties.getLocal().getDirectory(), relativePath);
        FileUtils.copyInputStreamToFile(in, storePath.toFile());
    }

    @Override
    public InputStream download(String relativePath) throws IOException {
        Assert.hasLength(relativePath, "relativePath must not be empty");
        Path storePath = Path.of(fileProperties.getLocal().getDirectory(), relativePath);
        if (!Files.exists(storePath)) {
            throw new BusinessException("file is not exists");
        }
        return Files.newInputStream(storePath);
    }

    @Override
    public void delete(String relativePath) throws IOException {
        Assert.hasLength(relativePath, "relativePath must not be empty");
        Path storePath = Path.of(fileProperties.getLocal().getDirectory(), relativePath);
        // 文件不存在返回 false
        if (Files.isDirectory(storePath)) {
            throw new RuntimeException(storePath + " is directory,can not delete ");
        }
        Files.deleteIfExists(storePath);
    }

    @Override
    public String getUrl(String relativePath) {
        return StringUtils.isNotBlank(relativePath) ? fileProperties.getUrlPrefix() + relativePath : null;
    }

    @Override
    public String getUrlPrefix() {
        return fileProperties.getUrlPrefix();
    }

    @Override
    public void close() throws Exception {

    }
}
