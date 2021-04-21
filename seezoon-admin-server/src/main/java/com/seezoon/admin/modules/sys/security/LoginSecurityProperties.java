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
     * 是否可以同时登录{@code true} 同个不可以同时登录
     */
    private boolean maxSessionsPreventsLogin = false;

    private Duration rememberTime = Duration.ofDays(7);;

}
