package com.seezoon.framework.component.file.handler;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author hdf
 */
public interface FileHandler extends AutoCloseable {

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
     * 删除文件
     *
     * @param relativePath
     * @throws IOException
     */
    void delete(String relativePath) throws IOException;

    /**
     * 文件可以访问的url路径
     *
     * @param relativePath
     * @return
     */
    String getUrl(String relativePath);

}
