package com.seezoon.framework.component.file.handler;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;

import net.coobird.thumbnailator.Thumbnails;

/**
 * @author hdf
 */
public interface FileHandler extends AutoCloseable {

    /**
     * 文件上传
     *
     * @param relativePath
     * @param contentType
     * @param in
     * @throws IOException
     */
    void upload(String relativePath, String contentType, InputStream in) throws IOException;

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

    String getUrlPrefix();

    default String getId(String relativePath) {
        if (StringUtils.isBlank(relativePath)) {
            return null;
        }
        int start = relativePath.lastIndexOf("/");
        if (-1 == start) {
            throw new IllegalArgumentException("相对路径错误");
        }
        int end = relativePath.lastIndexOf(".");
        if (-1 == end || end <= start) {
            throw new IllegalArgumentException("相对路径错误");
        }
        return relativePath.substring(start + 1, end);
    }

    default boolean isImage(String contentType) {
        if (StringUtils.isBlank(contentType)) {
            return false;
        }
        return contentType.startsWith("image/");
    }

    /**
     * 图片压缩
     *
     * @param source
     *            will be auto close
     * @param imageQuality
     *            输出质量
     * @param scale
     *            缩放
     * @return
     */
    default InputStream imageCompress(InputStream source, float imageQuality, double scale) throws IOException {
        try (InputStream in = source; ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            Thumbnails.of(in).outputQuality(imageQuality).scale(scale).toOutputStream(bos);
            ByteArrayInputStream compressedInputStream = new ByteArrayInputStream(bos.toByteArray());
            return compressedInputStream;
        }
    }
}
