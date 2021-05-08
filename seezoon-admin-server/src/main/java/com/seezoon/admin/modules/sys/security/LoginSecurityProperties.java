package com.seezoon.admin.modules.sys.security;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * @author hdf
 */
@ConfigurationProperties(prefix = "login")
@Getter
@Setter
public class LoginSecurityProperties {

    private Integer lockPasswdFailTimes = 5;
    private Integer lockIpFailTimes = 10;
    private Duration lockTime = Duration.ofDays(1);
    private boolean recordLog = true;
    /**
     * Controls the maximum number of sessions for a user when {@code maxSessionsPreventsLogin = true}
     */
    private int maximumSessions = 1;
    /**
     * 是否可以同时登录{@code true} 同个不可以同时登录
     */
    private boolean maxSessionsPreventsLogin = false;

    private Duration rememberTime = Duration.ofDays(7);
    /**
     * 可以定期更换
     */
    private String rememberKey = "C02tlRRi8JNsT6Bsp2liSE1paa5naDNY";

}
