package com.seezoon.generator.service;

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
    void generate() {
        systemGeneratorService.generate("sys_param");
    }
}