package com.seezoon.admin.modules.sys.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * @author hdf
 */
@ApiModel(value = "用户基本信息")
@Getter
@Setter
@RequiredArgsConstructor
public class UserInfo implements Serializable {

    @ApiModelProperty(value = "用户ID")
    private final Integer userId;
    @ApiModelProperty(value = "部门ID")
    private final Integer deptId;
    @ApiModelProperty(value = "登录账号")
    private final String username;
    @ApiModelProperty(value = "姓名")
    private final String name;
    @ApiModelProperty(value = "部门名称")
    private String deptName;
    @ApiModelProperty(value = "头像存储地址")
    private String photo;
    @ApiModelProperty(value = "头像")
    private String photoUrl;
    @ApiModelProperty(value = "手机号")
    private String mobile;
    @ApiModelProperty(value = "邮箱")
    private String email;
    @JsonIgnore
    private String dsf;
}
