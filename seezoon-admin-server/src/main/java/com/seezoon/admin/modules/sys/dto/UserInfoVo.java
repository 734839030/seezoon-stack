package com.seezoon.admin.modules.sys.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.seezoon.admin.modules.sys.dto.vue.VueRouteMenu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 前端需要的用户数据结构
 */
@Getter
@Setter
@AllArgsConstructor
public class UserInfoVo {
    private Integer userId;
    private String username;
    private String name;
    private Set<String> roles;
    private Set<String> permissions;
    // 树结构
    private List<VueRouteMenu> routes = new ArrayList<>();

}
