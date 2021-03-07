package com.seezoon.admin.modules.sys.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.seezoon.admin.modules.sys.dto.vue.VueRouteMenu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 前端需要的用户数据结构
 */
@ApiModel(value = "Vue菜单及权限")
@Getter
@Setter
@AllArgsConstructor
public class UserResourcesVo {
    @ApiModelProperty(value = "角色")
    private Set<String> roles;
    @ApiModelProperty(value = "权限")
    private Set<String> permissions;
    // 树结构
    @ApiModelProperty(value = "树形菜单")
    private List<VueRouteMenu> routes = new ArrayList<>();

}
