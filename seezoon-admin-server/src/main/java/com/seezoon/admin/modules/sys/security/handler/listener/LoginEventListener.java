package com.seezoon.admin.modules.sys.security.handler.listener;

import java.util.Date;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.google.common.eventbus.Subscribe;
import com.seezoon.admin.modules.sys.eventbus.AdminEventBus;
import com.seezoon.admin.modules.sys.service.SysLoginLogService;
import com.seezoon.dao.modules.sys.entity.SysLoginLog;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hdf
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class LoginEventListener implements InitializingBean {

    private final SysLoginLogService sysLoginLogService;

    @Subscribe
    public void listen(LoginResultMsg msg) {
        SysLoginLog sysLoginLog = new SysLoginLog();
        sysLoginLog.setUserId(msg.getUserId());
        sysLoginLog.setUserName(msg.getUserName());
        sysLoginLog.setIp(msg.getIp());
        sysLoginLog.setLoginTime(msg.getLoginTime());
        sysLoginLog.setResult(msg.getResult());
        sysLoginLog.setUserAgent(msg.getUserAgent());
        try {
            UserAgent userAgent = UserAgent.parseUserAgentString(msg.getUserAgent());
            Browser browser = userAgent.getBrowser();
            OperatingSystem os = userAgent.getOperatingSystem();
            String deviceName = os.getName() + " " + os.getDeviceType();
            String browserDetail = browser.getName() + " " + browser.getVersion(msg.getUserAgent());
            sysLoginLog.setBrowserName(browserDetail);
            sysLoginLog.setDeviceName(deviceName);
        } catch (Exception e) {
            log.error("parse userAgent error", e);
        }
        sysLoginLogService.save(sysLoginLog);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        AdminEventBus.register(this);
    }

    @RequiredArgsConstructor
    @Getter
    @Setter
    public static class LoginResultMsg {

        private final String userName;
        private final Date loginTime;
        private final String ip;
        private final String userAgent;
        private Integer result;
        private Integer userId;
    }
}
