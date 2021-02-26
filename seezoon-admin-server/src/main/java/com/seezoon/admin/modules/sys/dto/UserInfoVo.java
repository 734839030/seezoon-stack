package com.seezoon.admin.modules.sys.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.seezoon.admin.modules.sys.dto.vue.VueRouteMenu;

import lombok.Getter;
import lombok.Setter;

/**
 * 前端需要的用户数据结构
 */
@Getter
@Setter
public class UserInfoVo {
    private Integer userId;
    private String username;
    private String name;
    private Set<String> roles = new HashSet<>(1);
    private Set<String> permissions = new HashSet<>(1);
    // 树结构
    private List<VueRouteMenu> routes = new ArrayList<>();

}
