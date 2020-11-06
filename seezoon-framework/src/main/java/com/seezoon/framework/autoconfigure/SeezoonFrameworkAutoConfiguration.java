package com.seezoon.framework.autoconfigure;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * event listener 通过spring.factories 加载
 *
 * @author hdf
 */
@Configuration
@ServletComponentScan("com.seezoon.framework.servlet")
public class SeezoonFrameworkAutoConfiguration {

}
