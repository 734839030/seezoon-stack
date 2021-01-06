package com.seezoon.framework.component.file.handler;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author hdf
 */
public interface FileHandler {

    /**
     * 文件上传
     *
     * @param relativePath
     * @param in
     * @throws IOException
     */
    void upload(String relativePath, InputStream in) throws IOException;

    /**
     * 文件下载
     *
     * @param relativePath
     * @return
     * @throws IOException
     */
    InputStream download(String relativePath) throws IOException;

    /**
     * 文件可以访问的url路径
     *
     * @param relativePath
     * @return
     */
    String getUrl(String relativePath);

    /**
     * 销毁
     */
    void destroy();
}
