package com.seezoon.dao.modules.sys.entity;

import java.util.List;

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
 * 角色
 *
 * @author seezoon-generator 2021年1月25日 上午12:14:26
 */
@ApiModel(value = "角色")
@Getter
@Setter
@ToString
public class SysRole extends BaseEntity<Integer> {

    @ApiModelProperty(value = "角色编码", required = true)
    @NotBlank
    @Size(max = 50)
    private String code;

    @ApiModelProperty(value = "角色名称", required = true)
    @NotBlank
    @Size(max = 50)
    private String name;

    @ApiModelProperty(value = "数据范围:0:全部，1：本部门，2：本部门及以下，3.下级部门，4.指定部门，5.本人", required = true)
    @NotNull
    private Integer dataScope;

    @ApiModelProperty(value = "角色菜单")
    private List<Integer> menuIds;

}