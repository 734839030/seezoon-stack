package com.seezoon.framework.component;

import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.alibaba.fastjson.JSON;
import com.seezoon.framework.autoconfigure.SeezoonProperties;

/**
 * 异步配置
 *
 * 如需要使用异步，springboot 要求显示的用{@link @EnableAsync}
 */
@Configuration
public class AsyncCustomConfiguration extends AsyncConfigurerSupport {

    private static Logger logger = LoggerFactory.getLogger(AsyncCustomConfiguration.class);

    @Autowired
    private SeezoonProperties seezoonProperties;

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        // lambda表达式用作异常处理程序
        return (throwable, method, obj) -> {
            logger.error("Async exception method name :{},parmas:{}", method.getName(), JSON.toJSONString(obj),
                throwable);
        };
    }

    @Override
    public Executor getAsyncExecutor() {
        SeezoonProperties.AsyncProperties async = seezoonProperties.getAsync();
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(async.getCorePoolSize());
        executor.setMaxPoolSize(async.getMaxPoolSize());
        executor.setQueueCapacity(async.getQueueCapacity());
        executor.setKeepAliveSeconds(async.getKeepAliveTime());
        executor.setThreadNamePrefix(async.getThreadNamePrefix());
        executor.setWaitForTasksToCompleteOnShutdown(async.isWaitForTasksToCompleteOnShutdown());
        executor.setAwaitTerminationSeconds(async.getAwaitTerminationSeconds());
        executor.setTaskDecorator((runnable) -> {
            Map<String, String> copyOfContextMap = MDC.getCopyOfContextMap();
            return () -> {
                try {
                    if (null != copyOfContextMap) {
                        MDC.setContextMap(copyOfContextMap);
                    }
                    runnable.run();
                } finally {
                    MDC.clear();
                }
            };
        });
        // 线程池对拒绝任务的处理策略
        executor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                logger.error("Async exception:task rejected");
            }
        });
        // 初始化
        executor.initialize();
        return executor;
    }
}
