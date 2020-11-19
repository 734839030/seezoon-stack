package com.seezoon.generator;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author hdf
 */
@SpringBootApplication
public class GeneratorMain {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(GeneratorMain.class, args);
        System.in.read();
    }
}
