package com.seezoon.dao.modules.sys.entity;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 角色-菜单
 *
 */
@AllArgsConstructor
@Getter
@Setter
public class SysRoleMenu {

    /**
     * 角色
     */
    @NotNull
    private Integer roleId;

    /**
     * 菜单
     */
    @NotNull
    private Integer menuId;

}