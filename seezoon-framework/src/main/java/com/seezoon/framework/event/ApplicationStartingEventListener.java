package com.seezoon.framework.event;

import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

import com.seezoon.dao.framework.log.MdcHelper;

/**
 * 监听spring boot 应用启动开始事件
 *
 * @see <a href=
 *      "https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/reference/htmlsingle/#boot-features-application-events-and-listeners">boot-features-application-events-and-listeners</a>
 * @author hdf
 */
public class ApplicationStartingEventListener implements ApplicationListener<ApplicationStartingEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartingEvent event) {
        // 添加启动线程号
        MdcHelper.put();
    }
}
