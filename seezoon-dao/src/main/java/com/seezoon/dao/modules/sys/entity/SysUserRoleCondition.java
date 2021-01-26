package com.seezoon.dao.modules.sys.entity;

import com.seezoon.dao.framework.entity.PageCondition;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户-角色
 *
 * @author seezoon-generator 2021年1月26日 下午11:14:43
 */
@Getter
@Setter
@ToString
public class SysUserRoleCondition extends PageCondition {

    /**
     * 用户编号
     */
    private Integer userId;
    /**
     * 角色编号
     */
    private Integer roleId;

    /**
     * 状态
     */
    private Integer status;

}