package com.seezoon.framework.component.file.handler;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.OSSObject;
import com.seezoon.framework.properties.SeezoonProperties;

import lombok.RequiredArgsConstructor;

/**
 * @author hdf
 */
@RequiredArgsConstructor
public class AliyunOssHandler implements FileHandler, InitializingBean {

    private final SeezoonProperties.FileProperties.AliyunOssProperties aliyunOssProperties;
    private OSS ossClient;

    @Override
    public void upload(String relativePath, InputStream in) throws IOException {
        ossClient.putObject(aliyunOssProperties.getBucketName(), handleRelativePath(relativePath), in);
        if (null != in) {
            in.close();
        }
    }

    @Override
    public InputStream download(String relativePath) throws IOException {
        OSSObject object =
            ossClient.getObject(aliyunOssProperties.getBucketName(), this.handleRelativePath(relativePath));
        return object.getObjectContent();
    }

    @Override
    public void delete(String relativePath) throws IOException {
        ossClient.deleteObject(aliyunOssProperties.getBucketName(), this.handleRelativePath(relativePath));
    }

    @Override
    public String getUrl(String relativePath) {
        return StringUtils.isNotBlank(relativePath) ? aliyunOssProperties.getUrlPrefix() + relativePath : null;
    }

    @Override
    public void close() throws Exception {
        if (null != ossClient) {
            ossClient.shutdown();
        }
    }

    /**
     * 阿里云路径不能以/ 开始
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
    public void afterPropertiesSet() throws Exception {
        ossClient = new OSSClientBuilder().build(aliyunOssProperties.getEndpoint(),
            aliyunOssProperties.getAccessKeyId(), aliyunOssProperties.getAccessKeySecret());
    }
}
