package com.seezoon.framework.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

/**
 * 监听spring boot 初始化完成，可以对外服务开始事件
 *
 * @author hdf
 * @see <a href=
 *      "https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/reference/htmlsingle/#boot-features-application-events-and-listeners">boot-features-application-events-and-listeners</a>
 */
public class ApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationReadyEventListener.class);

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        logger.info("Application Is Ready For Service Requests");
    }

}
