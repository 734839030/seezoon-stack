package com.seezoon.framework.component.file.handler;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.seezoon.framework.properties.SeezoonProperties;

import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;

/**
 * @author bunco
 */
public class MinioHandler implements FileHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private SeezoonProperties.FileProperties fileProperties;
    private MinioClient minioClient;

    public MinioHandler(SeezoonProperties.FileProperties fileProperties) {
        this.fileProperties = fileProperties;
        this.minioClient = MinioClient.builder().endpoint(fileProperties.getMinio().getEndpoint())
            .credentials(fileProperties.getMinio().getAccessKey(), fileProperties.getMinio().getSecretKey()).build();
        try {
            if (!this.minioClient
                .bucketExists(BucketExistsArgs.builder().bucket(fileProperties.getMinio().getBucketName()).build())) {
                this.minioClient
                    .makeBucket(MakeBucketArgs.builder().bucket(fileProperties.getMinio().getBucketName()).build());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void upload(String relativePath, String contentType, InputStream in) throws IOException {
        if (isImage(contentType) && fileProperties.isEnableImageCompress()) {
            InputStream compressedInputStream =
                imageCompress(in, fileProperties.getImageQuality(), fileProperties.getIamgeScale());
            try {
                this.minioClient.putObject(PutObjectArgs.builder().bucket(fileProperties.getMinio().getBucketName())
                    .object(handleRelativePath(relativePath)).stream(compressedInputStream, -1, 10485760).build());
            } catch (Exception e) {
                // 正规项目自定义异常及错误码、文案
                throw new RuntimeException(e);
            }
        } else {
            try {
                this.minioClient.putObject(PutObjectArgs.builder().bucket(fileProperties.getMinio().getBucketName())
                    .object(handleRelativePath(relativePath)).stream(in, -1, 10485760).build());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if (null != in) {
            in.close();
        }
    }

    @Override
    public InputStream download(String relativePath) throws IOException {
        InputStream stream = null;
        try {
            stream =
                this.minioClient.getObject(GetObjectArgs.builder().bucket(fileProperties.getMinio().getBucketName())
                    .object(this.handleRelativePath(relativePath)).build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return stream;
    }

    @Override
    public void delete(String relativePath) throws IOException {
        try {
            this.minioClient.removeObject(RemoveObjectArgs.builder().bucket(fileProperties.getMinio().getBucketName())
                .object(this.handleRelativePath(relativePath)).build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getUrl(String relativePath) {
        return StringUtils.isNotBlank(relativePath) ? this.getUrlPrefix() + handleRelativePath(relativePath) : null;
    }

    @Override
    public void close() throws Exception {
        if (null != this.minioClient) {
            this.minioClient = null;
        }
    }

    /**
     * 路径不能以 / 开始
     *
     * @param relativePath
     * @return
     */
    private String handleRelativePath(String relativePath) {
        Assert.hasLength(relativePath, "relativePath is empty");
        if (relativePath.startsWith("/")) {
            relativePath = relativePath.substring(1);
        }
        return relativePath;
    }

    @Override
    public String getUrlPrefix() {
        return StringUtils.appendIfMissing(fileProperties.getUrlPrefix(), "/")
            + StringUtils.appendIfMissing(fileProperties.getMinio().getBucketName(), "/");
    }
}
