package com.seezoon.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

import com.seezoon.user.framework.properties.SeezoonUserProperties;

/**
 * 程序入口
 *
 * @author hdf
 */
@SpringBootApplication
@EnableConfigurationProperties({SeezoonUserProperties.class})
@EnableAsync
public class UserMain {

    public static void main(String[] args) {
        SpringApplication.run(UserMain.class, args);
    }
}
