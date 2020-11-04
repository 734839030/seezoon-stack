package com.seezoon.autoconfigure;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;

import com.seezoon.BaseSpringBootTest;
import com.zaxxer.hikari.HikariDataSource;

/**
 * @author hdf
 */
@EnableAutoConfiguration
public class SeezoonDaoAutoConfigurationTest extends BaseSpringBootTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testDataSourceIsRunning() {
        HikariDataSource hikariDataSource = applicationContext.getBean(HikariDataSource.class);
        assertTrue(hikariDataSource.isRunning());
    }
}