package com.seezoon.framework.component.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.seezoon.framework.component.file.handler.FileHandler;
import com.seezoon.framework.component.file.handler.LocalFileHandler;
import com.seezoon.framework.component.file.handler.NoneFileHandler;
import com.seezoon.framework.properties.SeezoonProperties;

/**
 * 文件上传下载处理
 *
 * @author hdf
 */
@Configuration(proxyBeanMethods = false)
public class FileHandlerConfiguration {

    @Autowired
    private SeezoonProperties seezoonProperties;

    @Bean
    @ConditionalOnProperty(name = "seezoon.file.store-type", havingValue = "none")
    public FileHandler noneFileHandler() {
        return new NoneFileHandler();
    }

    @Bean
    @Primary
    @ConditionalOnProperty(name = "seezoon.file.store-type", havingValue = "local")
    public FileHandler localFileHandler() {
        return new LocalFileHandler(seezoonProperties.getFile().getLocal());
    }

    @Bean
    @ConditionalOnProperty(name = "seezoon.file.store-type", havingValue = "aliyun_oss")
    public FileHandler aliyunOssFileHandler() {
        return new NoneFileHandler();
    }
}
