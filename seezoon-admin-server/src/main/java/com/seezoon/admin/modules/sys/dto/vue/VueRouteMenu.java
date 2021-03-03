package com.seezoon.admin.modules.sys.dto.vue;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.Setter;

/**
 * vue 路由菜单 后端直接算的，避免前端组装js明显卡顿
 */
@Getter
@Setter
public class VueRouteMenu {

    public static final String COMPONENT_LAYOUT = "LAYOUT";
    public static final String COMPONENT_IFRAME = "IFrame";

    private String path = StringUtils.EMPTY;
    private String name = StringUtils.EMPTY;
    private String component;
    private RouteMeta meta;
    private List<VueRouteMenu> children;
}
