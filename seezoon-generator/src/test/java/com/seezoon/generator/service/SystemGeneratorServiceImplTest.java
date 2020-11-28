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

    // 可多个也可以空
    public static final String[] DB_TABLES = {"sys_param"};
    @Autowired
    private SystemGeneratorService systemGeneratorService;

    /**
     * 参数为null 则生成全部表的代码
     *
     * 传表名则按指定生成，表名是可变参数
     *
     * @throws IOException
     */
    @Test
    public void generate() throws IOException {
        systemGeneratorService.generate(DB_TABLES);
    }
}