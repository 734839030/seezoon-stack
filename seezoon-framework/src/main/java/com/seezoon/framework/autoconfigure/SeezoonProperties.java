package com.seezoon.framework.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * @author hdf
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "seezoon")
public class SeezoonProperties {

    private DocProperties doc = new DocProperties();
    private HttpProperties http = new HttpProperties();
    private AsyncProperties async = new AsyncProperties();

    @Getter
    @Setter
    public static class DocProperties {
        private String name;
        private String version;
        private String url;
        private String description;
        private String author;
    }

    @Getter
    @Setter
    public static class HttpProperties {
        // 连接超时 ms
        private int connectTimeout = 6 * 1000;
        // 获取数据超时 ms
        private int socketTimeout = 6 * 1000;
        // 获取连接超时ms
        private int connectionRequestTimeout = 10 * 1000;
        // 最大线程数
        private int maxTotal = 100;
        // 站点最大连接数
        private int maxPerRoute = maxTotal;
        // 不活跃多久检查ms
        private int validateAfterInactivity = 60 * 1000;
        // 重试次数 0 不重试，
        private int retyTimes = 0;
        // 空闲时间多久销毁ms
        private int idleTimeToDead = 60 * 1000;
        // 连接最多存活多久ms
        private int connTimeToLive = 60 * 1000;
        // 清理空闲线程
        private int idleScanTime = 5 * 1000;
        // 默认请求头
        private String userAgent =
            "seezoon-framework " + "(" + System.getProperty("os.name") + "/" + System.getProperty("os.version") + "/"
                + System.getProperty("os.arch") + ";" + System.getProperty("java.version") + ")";
    }

    @Getter
    @Setter
    public static class AsyncProperties {
        private final int queueCapacity = 10000; // 缓冲队列数
        private String threadNamePrefix = "Async-Service-"; // 线程池名前缀
        private int corePoolSize = 1; // 核心线程数（默认线程数）
        private int maxPoolSize = 100; // 最大线程数
        private int keepAliveTime = 60; // 允许线程空闲时间（单位：默认为秒）
        private boolean waitForTasksToCompleteOnShutdown = true;
        private int awaitTerminationSeconds = 10;

    }
}
