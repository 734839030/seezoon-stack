package com.seezoon.admin.modules.sys.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hdf
 */
@ApiModel(value = "角色分配")
@Getter
@Setter
public class RoleAssignAo {

    @ApiModelProperty(value = "用户id", required = true)
    @NotEmpty
    private Integer[] userIds;

    @ApiModelProperty(value = "角色Id", required = true)
    @NotNull
    private Integer roleId;

    @ApiModelProperty(value = "分配true,移除false", required = true)
    @NotNull
    private Boolean addUser;

}
