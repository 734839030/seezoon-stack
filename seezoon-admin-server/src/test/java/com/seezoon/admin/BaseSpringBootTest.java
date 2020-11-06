package com.seezoon.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * spring boot test parent class,use junit 5 {@code org.junit.jupiter.api.Test} , so don’t add
 * {@code @RunWith(SpringRunner.class)}
 *
 * @author hdf
 * @date 2020/10/18 2:14 下午
 */
@SpringBootTest
public abstract class BaseSpringBootTest {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
}
