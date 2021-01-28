package com.seezoon.dao.modules.sys.entity;

import com.seezoon.dao.framework.entity.PageCondition;
import com.seezoon.dao.framework.sort.annotation.SortField;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户信息
 *
 * @author seezoon-generator 2021年1月16日 下午11:55:54
 */
@Getter
@Setter
@ToString
@SortField({"deptId:t.dept_id", "username:t.username", "mobile:t.mobile"})
public class SysUserCondition extends PageCondition {

    /**
     * 部门
     */
    @ApiModelProperty(value = "部门")
    private Integer deptId;
    /**
     * 登录名
     */
    @ApiModelProperty(value = "登录名")
    private String username;
    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    private String name;
    /**
     * 手机
     */
    @ApiModelProperty(value = "手机")
    private String mobile;
    /**
     * 手机
     */
    @ApiModelProperty(value = "状态1:正常,0:禁用")
    private Integer status;

    @ApiModelProperty(value = "角色")
    private Integer roleId;

    @ApiModelProperty(value = "拥有该角色true,不包含该角色false,和roleId一起使用才有效")
    private Boolean hasThisRole;

}