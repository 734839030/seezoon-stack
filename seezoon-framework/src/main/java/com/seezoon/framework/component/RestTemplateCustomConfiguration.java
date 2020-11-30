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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import com.seezoon.framework.autoconfigure.SeezoonProperties;

/**
 * 优化原生无连接池问题
 *
 * @author hdf
 */
@Configuration
public class RestTemplateCustomConfiguration {

    @Autowired
    private SeezoonProperties seezoonProperties;

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
