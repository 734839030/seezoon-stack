package com.seezoon.framework.log;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

/**
 * MDC 信息埋点
 *
 * @author hdf
 */
public class MdcHelper {

    public final static String HTTP_HEADER = "X-Trace-Id";
    private final static String THREAD_ID = "tid";
    private static final int LENGTH = 10;

    public static void put() {
        MDC.put(THREAD_ID, randomThreadId());
    }

    public static void put(String tid) {
        if (StringUtils.isNotEmpty(tid) && tid.length() == LENGTH) {
            MDC.put(THREAD_ID, tid);
        } else {
            put();
        }
    }

    public static String peek() {
        String tid = MDC.get(THREAD_ID);
        return StringUtils.isNotEmpty(tid) ? tid : null;
    }

    public static void clear() {
        MDC.remove(THREAD_ID);
    }

    private static String randomThreadId() {
        return RandomStringUtils.randomAlphanumeric(LENGTH);
    }
}
