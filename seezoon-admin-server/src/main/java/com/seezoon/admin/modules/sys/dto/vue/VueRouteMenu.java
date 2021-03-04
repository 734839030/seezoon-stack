package com.seezoon.admin.modules.sys.dto.vue;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;

import lombok.Getter;
import lombok.Setter;

/**
 * vue 路由菜单 后端直接算的，避免前端组装js明显卡顿
 */
@Getter
@Setter
public class VueRouteMenu {
    public static final String COMPONENT_LAYOUT = "LAYOUT";
    // 前端问题，没有定义常量
    public static final String COMPONENT_IFRAME = "/sys/iframe/FrameBlank";

    private String path = "/";
    private String name = RandomStringUtils.randomAlphanumeric(10);
    private String component;
    private RouteMeta meta;
    private List<VueRouteMenu> children;
}
