# 组件（更新中...）

在不增加复杂度的情况下，提供常用开发组件。

## 异步任务

Spring boot 可以通过@EnableAsync 开启异步，但是参数都是默认的，线程池，线程数，任务拒绝，任务超时等都不便于扩展。

> setTaskDecorator 实现主线程和异步线程的线程号串起来，方便定位问题。

```java
package com.seezoon.framework.component;

import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.alibaba.fastjson.JSON;
import com.seezoon.framework.properties.SeezoonProperties;

import lombok.RequiredArgsConstructor;

/**
 * 异步配置
 *
 * 如需要使用异步，springboot 要求显示的用{@code @EnableAsync}
 */
@Configuration
@RequiredArgsConstructor
public class AsyncCustomConfiguration extends AsyncConfigurerSupport {

    private static Logger logger = LoggerFactory.getLogger(AsyncCustomConfiguration.class);

    private final SeezoonProperties seezoonProperties;

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
        executor.setAllowCoreThreadTimeOut(async.isAllowCoreThreadTimeOut());
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

```

## Redis 组件

主要处理序列化方式。

```java
package com.seezoon.framework.component;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * RedisTemplate 等自定义，主要修改序列化方式
 *
 * @author hdf
 */
@Configuration(proxyBeanMethods = false)
public class RedisCustomConfiguration {

    @Bean
    public RedisTemplate<String, ?> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, ?> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer =
            new GenericJackson2JsonRedisSerializer();
        redisTemplate.setDefaultSerializer(genericJackson2JsonRedisSerializer);
        redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializer);
        return redisTemplate;
    }
}
```



## RestTemplate 

添加连接池，链接检测与回收、证书支持等。

```java
package com.seezoon.framework.component;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.StandardHttpRequestRetryHandler;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import com.seezoon.framework.properties.SeezoonProperties;

import lombok.RequiredArgsConstructor;

/**
 * 优化原生无连接池问题
 *
 * @author hdf
 */
@Configuration
@RequiredArgsConstructor
public class RestTemplateCustomConfiguration {

    private final SeezoonProperties seezoonProperties;

    /**
     * 获取ssl的连接池,适合双向请求,微信开发常用,该对象较重请缓存使用
     *
     * <code>
     *     KeyStore ks = KeyStore.getInstance("PKCS12");
     * 	   ks.load(inputStream, mchId.toCharArray());
     * 	   // Trust own CA and all self-signed certs
     * 	   SSLContext sslcontext = SSLContexts.custom()
     * 					.loadKeyMaterial(ks, mchId.toCharArray())
     * 					.build();
     * </code>
     *
     * @return
     */
    public RestTemplate getSslRestTemplate(SSLContext sslContext) {
        Assert.notNull(sslContext, "sslContext must not null");
        ClientHttpRequestFactory factory = clientHttpRequestFactory(sslContext);
        RestTemplate restTemplate = new RestTemplate(factory);
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate;
    }

    /**
     * 优化默认连接池
     *
     * @return
     */
    @Primary
    @Bean
    public RestTemplate restTemplate() {
        ClientHttpRequestFactory factory = clientHttpRequestFactory(null);
        RestTemplate restTemplate = new RestTemplate(factory);
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate;
    }

    public HttpClientConnectionManager poolingConnectionManager() {
        // 自定义连接池的话这个更准确，自动已连接池参数需要都在连接池上设置,默认池子 {@link org.apache.http.impl.client.HttpClientBuilder#build}
        // 自定义ssl 策略时候,请查看该方法构造函数
        PoolingHttpClientConnectionManager poolingConnectionManager = new PoolingHttpClientConnectionManager();

        poolingConnectionManager.setMaxTotal(seezoonProperties.getHttp().getMaxTotal()); // 连接池最大连接数
        poolingConnectionManager.setDefaultMaxPerRoute(seezoonProperties.getHttp().getMaxPerRoute()); // 每个主机的并发
        // 长连接
        poolingConnectionManager.setDefaultSocketConfig(SocketConfig.custom()
            .setSoTimeout(seezoonProperties.getHttp().getSocketTimeout()).setSoKeepAlive(true).build());
        // 连接不活跃多久检查毫秒 并不是100 % 可信
        poolingConnectionManager.setValidateAfterInactivity(seezoonProperties.getHttp().getValidateAfterInactivity());
        // 空闲扫描线程
        HttpClientIdleConnectionMonitor.registerConnectionManager(poolingConnectionManager,
            seezoonProperties.getHttp().getIdleTimeToDead(), seezoonProperties.getHttp().getIdleScanTime());
        return poolingConnectionManager;
    }

    public HttpClientBuilder httpClientBuilder(SSLContext sslContext) {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        // 设置HTTP连接管理器
        httpClientBuilder.setConnectionManager(poolingConnectionManager());
        RequestConfig requestConfig = RequestConfig.custom()
            .setConnectionRequestTimeout(seezoonProperties.getHttp().getConnectionRequestTimeout())// 获取连接等待时间
            .setConnectTimeout(seezoonProperties.getHttp().getConnectTimeout())// 连接超时
            .setSocketTimeout(seezoonProperties.getHttp().getSocketTimeout())// 获取数据超时
            .build();
        httpClientBuilder.setDefaultRequestConfig(requestConfig).setSSLContext(sslContext)
            .setUserAgent(seezoonProperties.getHttp().getUserAgent()).disableContentCompression()
            // 收动设置连接池 这个在连接池那里设置，查看该方法注释该,为了避免版本升级导致错误，也冗余设置
            .setConnectionTimeToLive(seezoonProperties.getHttp().getConnTimeToLive(), TimeUnit.MILLISECONDS)// 连接最大存活时间
            // 默认重试次数为0，POST 不重试
            .setRetryHandler(new StandardHttpRequestRetryHandler(seezoonProperties.getHttp().getRetyTimes(), false));
        return httpClientBuilder;
    }

    public ClientHttpRequestFactory clientHttpRequestFactory(SSLContext sslContext) {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setHttpClient(httpClientBuilder(sslContext).build());
        return clientHttpRequestFactory;
    }

    /**
     * 来自优秀开源代码
     *
     * @see <a>https://github.com/aliyun/aliyun-oss-java-sdk/blob/master/src/main/java/com/aliyun/oss/common/comm/IdleConnectionReaper.java</a>
     */
    public static final class HttpClientIdleConnectionMonitor extends Thread {

        private static final ArrayList<HttpClientConnectionManager> CONNECTION_MANAGERS = new ArrayList<>();
        private static Logger logger = LoggerFactory.getLogger(HttpClientIdleConnectionMonitor.class);
        private static long idleScanTime;
        private static HttpClientIdleConnectionMonitor instance;

        private static long idleConnectionTime;

        private volatile boolean shuttingDown;

        private HttpClientIdleConnectionMonitor(long idleConnectionTime, long idleScanTime) {
            super("Connection Manager Monitor");
            HttpClientIdleConnectionMonitor.idleConnectionTime = idleConnectionTime;
            HttpClientIdleConnectionMonitor.idleScanTime = idleScanTime;
            setDaemon(true);
        }

        /**
         * 如果 HttpClientIdleConnectionMonitor 已存在则设置的两个时间参数不生效
         *
         * @param connectionManager
         * @param idleConnectionTime
         * @param idleScanTime
         * @return
         */
        public static synchronized boolean registerConnectionManager(HttpClientConnectionManager connectionManager,
            long idleConnectionTime, long idleScanTime) {
            if (instance == null) {
                instance = new HttpClientIdleConnectionMonitor(idleConnectionTime, idleScanTime);
                instance.start();
            }
            return CONNECTION_MANAGERS.add(connectionManager);
        }

        public static synchronized boolean removeConnectionManager(HttpClientConnectionManager connectionManager) {
            boolean b = CONNECTION_MANAGERS.remove(connectionManager);
            if (CONNECTION_MANAGERS.isEmpty()) {
                shutdown();
            }
            return b;
        }

        public static synchronized boolean shutdown() {
            if (instance != null) {
                instance.markShuttingDown();
                instance.interrupt();
                CONNECTION_MANAGERS.clear();
                instance = null;
                return true;
            }
            return false;
        }

        public static synchronized int size() {
            return CONNECTION_MANAGERS.size();
        }

        public static synchronized void setIdleConnectionTime(long idletime) {
            idleConnectionTime = idletime;
        }

        private void markShuttingDown() {
            shuttingDown = true;
        }

        @SuppressWarnings("unchecked")
        @Override
        public void run() {
            while (true) {
                if (shuttingDown) {
                    logger.debug("Shutting down Connection Manager Monitor thread.");
                    return;
                }

                try {
                    Thread.sleep(idleScanTime);
                } catch (InterruptedException e) {
                }

                try {
                    List<HttpClientConnectionManager> connectionManagers = null;
                    synchronized (HttpClientIdleConnectionMonitor.class) {
                        connectionManagers = (List<
                            HttpClientConnectionManager>)HttpClientIdleConnectionMonitor.CONNECTION_MANAGERS.clone();
                    }
                    for (HttpClientConnectionManager connectionManager : connectionManagers) {
                        try {
                            connectionManager.closeExpiredConnections();
                            connectionManager.closeIdleConnections(idleConnectionTime, TimeUnit.MILLISECONDS);
                        } catch (Exception ex) {
                            logger.warn("Unable to close idle connections", ex);
                        }
                    }
                } catch (Throwable t) {
                    logger.debug("Connection Manager Monitor thread: ", t);
                }
            }
        }
    }
}
```

## [Task Execution and Scheduling](https://docs.spring.io/spring-boot/docs/2.4.5/reference/htmlsingle/#boot-features-task-execution-scheduling)

连接池，链接拒绝等支持，默认的也够用但是开启后线程会增多，需要手动配置，所以选择自定义实现下。

```java
package com.seezoon.framework.component;

import java.util.concurrent.ScheduledThreadPoolExecutor;
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
 * 定时配置可选
 * 
 * @see <a>https://docs.spring.io/spring-boot/docs/2.4.5/reference/htmlsingle/#boot-features-task-execution-sch</a>
 *      如需要使用异步，springboot 要求显示的用{@code @EnableScheduling}
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
        scheduledThreadPoolExecutor.setRejectedExecutionHandler((r, executor) -> {
            logger.error("Scheduled exception:scheduled rejected");
        });
        taskRegistrar.setScheduler(scheduledThreadPoolExecutor);
    }
}
```

##  [Caching](https://docs.spring.io/spring-boot/docs/2.4.5/reference/htmlsingle/#boot-features-caching)(caffeine)

简易内存缓存，适合对分布式场景不一致不敏感的场景，如字典，非关键参数。

该框架默认配置了。

```properties
spring.cache.type=caffeine
spring.cache.caffeine.spec=maximumSize=1000,expireAfterAccess=300s
```

缓存用法，具体请参考参考文档。

```java
@Cacheable(cacheNames = "SysParam", key = "#paramKey")
```

## 文件存储

提供通过配置实现文件存储类型更换，使用其他存储可以自行扩展。

```
com.seezoon.framework.component.file.handler.FileHandler
com.seezoon.framework.component.file.FileHandlerConfiguration
```

- 本地文件(多实例可以使用NFS)

  ```properties
  seezoon.file.store-type=local
  seezoon.file.url-prefix=http://127.0.0.1:3001/static
  # 本地适用，其他部署环境写真实目录
  seezoon.file.local.directory=@local.upload.directory@
  ```

- 阿里云OSS

  ```properties
  seezoon.file.store-type=aliyun_oss
  seezoon.file.url-prefix=https://seezoon-file.oss-cn-hangzhou.aliyuncs.com
  seezoon.file.aliyun.bucket-name=seezoon-file
  seezoon.file.aliyun.endpoint=oss-cn-hangzhou.aliyuncs.com
  seezoon.file.aliyun.access-key-id=xxxx
  seezoon.file.aliyun.access-key-secret=xxx
  ```

  

  