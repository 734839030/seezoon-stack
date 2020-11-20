package com.seezoon.generator;

import java.io.IOException;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author hdf
 */
@SpringBootApplication
@MapperScan("com.seezoon.generator.dao")
public class GeneratorMain {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(GeneratorMain.class, args);
        System.in.read();
    }
}
