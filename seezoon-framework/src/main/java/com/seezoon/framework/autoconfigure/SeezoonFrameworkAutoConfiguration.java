package com.seezoon.framework.autoconfigure;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import com.seezoon.framework.web.advice.ExceptionAdvice;
import com.seezoon.framework.web.advice.ParamBindAdvice;

/**
 * event listener 通过spring.factories 加载
 *
 * @author hdf
 */
@Configuration
@PropertySource({"classpath:default-framework.properties"})
@ServletComponentScan("com.seezoon.framework.servlet")
@Import({ExceptionAdvice.class, ParamBindAdvice.class})
public class SeezoonFrameworkAutoConfiguration {

}
