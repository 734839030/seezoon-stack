package com.seezoon.generator.service;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author hdf
 */
@SpringBootTest
class SystemGeneratorServiceImplTest {

    @Autowired
    private SystemGeneratorServiceImpl systemGeneratorService;

    @Test
    void generate() throws IOException {
        systemGeneratorService.generate(null);
    }
}