package com.seezoon.framework.component.file.handler;

import java.io.IOException;
import java.io.InputStream;

/**
 * 文件上传空实现，让{@code @Autowire} 默认注入文件处理类不报错
 *
 * @author hdf
 */
public class NoneFileHandler implements FileHandler {

    @Override
    public void upload(String relativePath, InputStream in) throws IOException {
        throw new RuntimeException("pls config FileHandler properties");
    }

    @Override
    public InputStream download(String relativePath) throws IOException {
        throw new RuntimeException("pls config FileHandler properties");
    }

    @Override
    public void delete(String relativePath) throws IOException {
        throw new RuntimeException("pls config FileHandler properties");
    }

    @Override
    public String getUrl(String relativePath) {
        return relativePath;
    }

    @Override
    public void close() throws Exception {

    }
}
