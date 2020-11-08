package com.seezoon.framework.autoconfigure;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * event listener 通过spring.factories 加载
 *
 * @author hdf
 */
@Configuration
@PropertySource({"classpath:default-framework.properties"})
@ServletComponentScan("com.seezoon.framework.servlet")
// 扫描advice 也可以使用@import
@ComponentScan("com.seezoon.framework.web.advice")
public class SeezoonFrameworkAutoConfiguration {

}
