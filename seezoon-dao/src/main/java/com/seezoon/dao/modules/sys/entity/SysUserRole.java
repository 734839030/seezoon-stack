package com.seezoon.dao.modules.sys.entity;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户-角色
 *
 * @author seezoon-generator 2021年1月26日 下午11:28:50
 */
@AllArgsConstructor
@Getter
@Setter
public class SysUserRole {

    /**
     * 用户id
     */
    @NotNull
    private Integer userId;

    /**
     * 角色Id
     */
    @NotNull
    private Integer roleId;

}