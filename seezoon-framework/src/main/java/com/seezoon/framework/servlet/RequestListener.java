package com.seezoon.framework.servlet;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import com.seezoon.framework.log.MdcHelper;

import lombok.extern.slf4j.Slf4j;

/**
 * 早于filter 执行，处理请求到来基本信息
 *
 * <pre>
 *     1.调用链追踪信息
 * </pre>
 *
 * @author hdf
 */
@WebListener
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class RequestListener implements ServletRequestListener {

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        HttpServletRequest servletRequest = (HttpServletRequest)sre.getServletRequest();
        // 处理调用链信息
        String traceId = servletRequest.getHeader(MdcHelper.HTTP_HEADER);
        MdcHelper.put(traceId);

    }

    /**
     * 链路中有异常也会执行
     *
     * @param sre
     */
    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        MdcHelper.clear();
    }
}