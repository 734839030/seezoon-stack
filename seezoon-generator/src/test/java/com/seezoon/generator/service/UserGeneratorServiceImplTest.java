package com.seezoon.generator.service;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.seezoon.generator.plan.UserTablePlanParam;

/**
 * @author hdf
 */
@SpringBootTest
class UserGeneratorServiceImplTest {

    @Autowired
    UserGeneratorServiceImpl userGeneratorServiceImpl;

    @Test
    public void generate() throws IOException {
        userGeneratorServiceImpl.generate(new UserTablePlanParam());
    }
}