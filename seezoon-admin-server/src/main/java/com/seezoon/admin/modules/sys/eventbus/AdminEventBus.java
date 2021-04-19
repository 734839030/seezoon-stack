package com.seezoon.admin.modules.sys.eventbus;

import com.google.common.eventbus.EventBus;

/**
 * @author hdf
 */
public class AdminEventBus {

    private static final String EVENT_BUS_NAME = "admin";
    private static final EventBus INSTANCE = new EventBus(EVENT_BUS_NAME);

    private AdminEventBus() {}

    public static void publish(Object object) {
        INSTANCE.post(object);
    }

    public static void register(Object object) {
        INSTANCE.register(object);
    }
}
