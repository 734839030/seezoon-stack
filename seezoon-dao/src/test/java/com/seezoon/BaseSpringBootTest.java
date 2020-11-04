package com.seezoon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

/**
 * spring boot test parent class,use junit 5 {@code org.junit.jupiter.api.Test} , so don’t add
 * {@code @RunWith(SpringRunner.class)}
 *
 * 在无@SpringBootApplication的时候子类要加@EnableAutoConfiguration
 *
 * @author hdf
 * @date 2020/10/18 2:14 下午
 */
@SpringBootTest(classes = {BaseSpringBootTest.class})
// 不写basePackages,默认为当前包下
@ComponentScan
// 这里这样可以保留classpath下的application.properties，如果test resources中存在application.properties，则classpath 不加载
@ActiveProfiles("local")
public class BaseSpringBootTest {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
}
