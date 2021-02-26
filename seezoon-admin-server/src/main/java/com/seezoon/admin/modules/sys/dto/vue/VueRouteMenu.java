package com.seezoon.admin.modules.sys.dto.vue;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * vue 路由菜单 后端直接算的，避免前端组装js明显卡顿
 */
@Getter
@Setter
public class VueRouteMenu {
    private String path;
    private String name;
    private String component;
    private RouteMeta meta;
    private List<VueRouteMenu> children;
}
