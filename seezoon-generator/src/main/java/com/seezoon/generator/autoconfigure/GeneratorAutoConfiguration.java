package com.seezoon.generator.autoconfigure;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author hdf
 */
@Configuration
@MapperScan("com.seezoon.generator.dao")
@ComponentScan("com.seezoon.generator.service")
public class GeneratorAutoConfiguration {

}
