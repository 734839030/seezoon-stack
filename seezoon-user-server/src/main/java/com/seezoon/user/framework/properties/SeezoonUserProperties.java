package com.seezoon.user.framework.properties;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * @author hdf
 */
@ConfigurationProperties(prefix = "seezoon.user")
@Getter
@Setter
public class SeezoonUserProperties {

    private LoginProperties login = new LoginProperties();

    @Getter
    @Setter
    public class LoginProperties {

        /**
         * 开启session 并发控制，true 下面才maximumSessions & maxSessionsPreventsLogin才生效
         */
        private boolean concurrentSessionControlEnabled = true;
        /**
         * Controls the maximum number of sessions for a user where sessionConcurrency =true
         */
        private int maximumSessions = 1;
        /**
         * 后面一个登陆{@code true} 登陆报错，false 后面踢前面,when sessionConcurrency=true
         */
        private boolean maxSessionsPreventsLogin = false;

        private Duration rememberTime = Duration.ofDays(7);
        /**
         * 可以定期更换
         */
        private String rememberKey = "C02tlRci8JNsT6Bsp2l0SE1paa5naDNY";

    }
}
