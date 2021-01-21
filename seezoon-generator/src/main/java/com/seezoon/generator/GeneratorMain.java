package com.seezoon.generator;

import java.io.IOException;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.seezoon.generator.service.SystemGeneratorService;

/**
 * @author hdf
 */
@SpringBootApplication
@MapperScan("com.seezoon.generator.dao")
public class GeneratorMain implements CommandLineRunner {

    private final SystemGeneratorService systemGeneratorService;

    public GeneratorMain(SystemGeneratorService systemGeneratorService) {
        this.systemGeneratorService = systemGeneratorService;
    }

    public static void main(String[] tables) throws IOException {
        SpringApplication.run(GeneratorMain.class, tables);
    }

    @Override
    public void run(String... tables) throws Exception {
        systemGeneratorService.generate(tables);
    }
}
