package com.seezoon.framework.component;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import com.seezoon.framework.properties.SeezoonProperties;
import com.seezoon.framework.properties.SeezoonProperties.ScheduledProperties;

import lombok.RequiredArgsConstructor;

/**
 * 定时配置
 *
 * 如需要使用异步，springboot 要求显示的用{@code @EnableScheduling}
 */
@Configuration
@RequiredArgsConstructor
public class ScheduledCustomConfiguration implements SchedulingConfigurer {

    private static Logger logger = LoggerFactory.getLogger(ScheduledCustomConfiguration.class);

    private final SeezoonProperties seezoonProperties;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        ScheduledProperties scheduled = seezoonProperties.getScheduled();
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor =
            new ScheduledThreadPoolExecutor(scheduled.getCorePoolSize());
        scheduledThreadPoolExecutor.setMaximumPoolSize(scheduled.getMaxPoolSize());
        scheduledThreadPoolExecutor.setKeepAliveTime(scheduled.getKeepAliveTime(), TimeUnit.SECONDS);
        scheduledThreadPoolExecutor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                logger.error("Scheduled exception:scheduled rejected");
            }
        });
        taskRegistrar.setScheduler(scheduledThreadPoolExecutor);
    }
}
