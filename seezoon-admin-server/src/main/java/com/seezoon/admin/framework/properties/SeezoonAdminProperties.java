package com.seezoon.admin.framework.properties;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * @author hdf
 */
@ConfigurationProperties(prefix = "seezoon.admin")
@Getter
@Setter
public class SeezoonAdminProperties {

    private LoginProperties login = new LoginProperties();

    @Getter
    @Setter
    public class LoginProperties {

        private Integer lockPasswdFailTimes = 5;
        private Integer lockIpFailTimes = 10;
        private Duration lockTime = Duration.ofDays(1);
        private boolean recordLog = true;

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
        private String rememberKey = "C02tlRRi8JNsT6Bsp2liSE1paa5naDNY";

    }
}
