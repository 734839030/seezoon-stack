package com.seezoon.framework.autoconfigure;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author hdf
 */
@SpringBootTest(classes = {SeezoonFrameworkAutoConfigurationTest.class})
@EnableAutoConfiguration
public class SeezoonFrameworkAutoConfigurationTest {

    @Test
    public void start() {
        System.out.println("success");
    }
}