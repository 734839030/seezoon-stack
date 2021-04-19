package com.seezoon.framework.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

/**
 * @author hdf
 */
public class IpUtil {

    public static String getRemoteIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (StringUtils.isBlank(ipAddress)) {
            ipAddress = request.getRemoteHost();
        }
        if (StringUtils.isBlank(ipAddress)) {
            return null;
        }

        String[] ips = StringUtils.split(ipAddress, ",");
        return ips[0];
    }
}
