package com.seezoon.admin.modules.sys.dto.vue;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * vue 路由菜单 后端直接算的，避免前端组装js明显卡顿
 */
@ApiModel(value = "Vue菜单")
@Getter
@Setter
public class VueRouteMenu {
    public static final String COMPONENT_LAYOUT = "LAYOUT";
    // 前端问题，没有定义常量
    public static final String COMPONENT_IFRAME = "/sys/iframe/FrameBlank";

    @ApiModelProperty(value = "路由路径")
    private String path = "/";
    @ApiModelProperty(value = "路由名称")
    private String name = RandomStringUtils.randomAlphanumeric(10);
    @ApiModelProperty(value = "组件路径")
    private String component;
    @ApiModelProperty(value = "路由元数据")
    private RouteMeta meta;
    @ApiModelProperty(value = "子路由")
    private List<VueRouteMenu> children;
}
