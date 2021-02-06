package com.seezoon.generator;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

/**
 * 通过main 生成代码，args 为空时候生成全部
 *
 * 改测试会执行执行{@link GeneratorMain}
 *
 * @author hdf
 */
@SpringBootTest(args = {"sys_role_menu"})
@Slf4j
class GeneratorMainTest {

    @Test
    void main() {
        log.info("generate code by test");
    }
}