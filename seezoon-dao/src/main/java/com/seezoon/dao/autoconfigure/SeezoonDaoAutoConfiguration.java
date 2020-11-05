package com.seezoon.dao.autoconfigure;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.retry.support.RetryTemplate;

import com.zaxxer.hikari.HikariDataSource;

import lombok.RequiredArgsConstructor;

/**
 * @author hdf
 */
@Configuration
@MapperScan("com.seezoon.dao.modules.*")
// 可以省略，但idea dao 注入有错误提示规避
@ComponentScan("com.seezoon.dao.modules.*")
// 这个配置名字不能用application.properties
@PropertySource("classpath:default-datasource.properties")
@AutoConfigureAfter({MybatisAutoConfiguration.class})
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SeezoonDaoAutoConfiguration implements InitializingBean {

    private final ApplicationContext applicationContext;

    /**
     * hikari 默认不会初始化DB
     */
    private void dataSourceInit() {
        HikariDataSource hikariDataSource = applicationContext.getBean(HikariDataSource.class);
        if (hikariDataSource.isRunning()) {
            return;
        }

        // 首次2s后重试,下次 4 ，8 ，16
        // 最少1分钟一次
        RetryTemplate retryTemplate = RetryTemplate.builder().infiniteRetry()
            .exponentialBackoff(Duration.ofSeconds(2).toMillis(), 2, Duration.ofMinutes(1).toMillis()).build();
        retryTemplate.execute((context) -> {
            // 自动关闭，释放连接
            try (Connection ignored = hikariDataSource.getConnection();) {
            } catch (SQLException e) {
                throw new RuntimeException(String.format("Datasource Init Error attempt {} times ,Because {}",
                    context.getRetryCount() + 1, e.getMessage()));
            }
            return 0;
        });
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.dataSourceInit();
    }
}
