package com.seezoon.framework.autoconfigure;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author hdf
 */
@Configuration
@ServletComponentScan("com.seezoon.framework.servlet")
public class SeezoonFrameworkAutoConfiguration {

}
