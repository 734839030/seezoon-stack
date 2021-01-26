package com.seezoon.dao.modules.sys.entity;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.seezoon.dao.framework.entity.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户-角色
 *
 * @author seezoon-generator 2021年1月26日 下午11:28:50
 */
@ApiModel
@Getter
@Setter
@ToString
public class SysUserRole extends BaseEntity<Integer> {

    @ApiModelProperty(value = "用户编号", required = true)
    @NotNull
    private Integer userId;

    @ApiModelProperty(value = "角色编号", required = true)
    @NotNull
    private Integer roleId;


}