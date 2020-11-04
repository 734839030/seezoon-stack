package com.seezoon.framework.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.context.ApplicationListener;

/**
 * 监听spring boot 初始化失败事件,可以配置告警逻辑
 *
 * @see <a href=
 *      "https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/reference/htmlsingle/#boot-features-application-events-and-listeners">boot-features-application-events-and-listeners</a>
 * @author hdf
 */
public class ApplicationFailedEventListener implements ApplicationListener<ApplicationFailedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationFailedEventListener.class);

    @Override
    public void onApplicationEvent(ApplicationFailedEvent event) {
        logger.info("Application Start Failed");
    }
}
