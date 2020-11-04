package com.seezoon.dao.framework.servlet.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

import com.seezoon.dao.framework.log.MdcHelper;

/**
 * 请求线程号埋点
 *
 * @author hdf
 */
@WebListener
public class RequestThreadIdListener implements ServletRequestListener {

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        MdcHelper.put();
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