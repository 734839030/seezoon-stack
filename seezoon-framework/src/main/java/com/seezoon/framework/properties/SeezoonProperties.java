package com.seezoon.framework.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;

import com.seezoon.framework.component.file.StoreType;

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
    private CorsProperties cors = new CorsProperties();
    private ScheduledProperties scheduled = new ScheduledProperties();
    private FileProperties file = new FileProperties();

    /**
     * 文档相关
     */
    @Getter
    @Setter
    public static class DocProperties {
        private String name;
        private String version;
        private String url;
        private String description;
        private String author;
    }

    /**
     * rest template 相关
     */
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

    /**
     * 异步处理配置
     */
    @Getter
    @Setter
    public static class AsyncProperties {
        private final int queueCapacity = 10000; // 缓冲队列数
        private String threadNamePrefix = "Async-Service-"; // 线程池名前缀
        private int corePoolSize = 1; // 核心线程数（默认线程数）
        private boolean allowCoreThreadTimeOut = true; // core size 可以根据自动缩到0
        private int maxPoolSize = 100; // 最大线程数
        private int keepAliveTime = 60; // 允许线程空闲时间（单位：默认为秒）
        private boolean waitForTasksToCompleteOnShutdown = true;
        private int awaitTerminationSeconds = 10;
    }

    /**
     * 前后端分离，跨域很常见，框架默认开启，线上为了安全可以设置allowedOrigins
     *
     * also see {@link org.springframework.web.cors.CorsConfiguration}
     */
    @Getter
    @Setter
    public static class CorsProperties {

        private boolean enable = false;
        /**
         * 路径{@link org.springframework.util.AntPathMatcher}
         */
        private String mapping = "/**";
        // 配置时候可以逗号分隔,可以写实际域名,如https://www.seeezoon.com
        private String[] allowedOrigins = {CorsConfiguration.ALL};
        private String[] allowedMethods = {CorsConfiguration.ALL};
        private String[] allowedHeaders = {CorsConfiguration.ALL};
        private boolean allowCredentials = true;
        private long maxAge = 1800;
    }

    /**
     * 定时任务线程配置
     */
    @Getter
    @Setter
    public static class ScheduledProperties {
        private int corePoolSize = 1; // 核心线程数（默认线程数）
        private int maxPoolSize = 100; // 最大线程数
        private int keepAliveTime = 60; // 允许线程空闲时间（单位：默认为秒）
    }

    /**
     * 文件上传配置
     */
    @Getter
    @Setter
    public static class FileProperties {
        private StoreType storeType = StoreType.NONE;
        /**
         * 可访问的网址前缀，如https://xxx.com
         */
        private String urlPrefix;
        // 图片压缩开关
        private boolean enableImageCompress = false;
        // 图片输出质量 1 百分百输出 < 1
        private float imageQuality = 1;
        // 缩放1 为不缩放 < 1
        private double iamgeScale = 1;

        private LocalProperties local = new LocalProperties();
        private AliyunOssProperties aliyun = new AliyunOssProperties();

        @Getter
        @Setter
        public static class LocalProperties {
            /**
             * 存储目录，一般为可以处理静态容器的路径，如nginx,tomcat 下可以通过url访问的目录,如果/data/files
             *
             * @local.upload.directory@ for local test
             */
            private String directory;
        }

        @Getter
        @Setter
        public static class AliyunOssProperties {
            /**
             * 阿里云oss 存储空间
             * <a>https://help.aliyun.com/document_detail/177682.html?spm=a2c4g.11186623.6.634.4a102c4cZPZ0M5</a>
             */
            private String bucketName;
            /**
             * 对外可访问的域名,线上推荐用内网，开发测试如果无法连接则使用公网
             * <a>https://help.aliyun.com/document_detail/31837.html?spm=a2c4g.11186623.6.625.3e4933e1OCqmWA</a>
             */
            private String endpoint;
            /**
             * 分配的ID
             */
            private String accessKeyId;
            /**
             * 分配密钥
             */
            private String accessKeySecret;
        }

    }
}
