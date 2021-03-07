package com.seezoon.admin.modules.sys.dto.vue;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@ApiModel(value = "路由元数据")
@RequiredArgsConstructor
@Getter
public class RouteMeta {
    @ApiModelProperty(value = "名称")
    private final String title;
    @ApiModelProperty(value = "图标")
    private final String icon;
    @ApiModelProperty(value = "内部链接")
    private String frameSrc;

    public void setFrameSrc(String frameSrc) {
        this.frameSrc = frameSrc;
    }
}
