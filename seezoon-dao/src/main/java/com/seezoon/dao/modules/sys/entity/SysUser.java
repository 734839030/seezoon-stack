package com.seezoon.dao.modules.sys.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seezoon.dao.framework.entity.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户信息
 *
 * @author seezoon-generator 2021年1月16日 下午11:55:54
 */
@ApiModel
@Getter
@Setter
@ToString
public class SysUser extends BaseEntity<Integer> {

    @ApiModelProperty(value = "部门")
    private Integer deptId;

    @ApiModelProperty(value = "登录名", required = true)
    @NotBlank
    @Size(max = 50)
    private String username;

    @ApiModelProperty(value = "密码,新增必输")
    // @NotBlank
    @Size(max = 100)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @ApiModelProperty(value = "姓名", required = true)
    @NotBlank
    @Size(max = 50)
    private String name;

    @ApiModelProperty(value = "手机")
    private String mobile;

    @ApiModelProperty(value = "头像")
    private String photo;

    @ApiModelProperty(value = "邮件")
    private String email;

    /**
     * 以下为join 字段
     */
    @ApiModelProperty(value = "父部门名称(只显示)")
    private String deptName;

}