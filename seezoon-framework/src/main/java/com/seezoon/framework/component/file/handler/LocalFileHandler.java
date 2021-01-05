package com.seezoon.framework.component.file.handler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * 本地文件存储处理
 *
 * @author hdf
 */
public class LocalFileHandler implements FileHandler {

    @Override
    public void upload(String relativePath, InputStream in) throws IOException {

    }

    @Override
    public InputStream download(String relativePath) throws FileNotFoundException {
        return null;
    }

    @Override
    public String getUrl(String relativePath) {
        return null;
    }

    @Override
    public void destroy() {

    }
}
