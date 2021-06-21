package com.seezoon.user.modules.security.dto;

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

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "用户ID")
    private final Integer userId;
    @ApiModelProperty(value = "昵称")
    private String nickName;
    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "头像")
    private String photoUrl;
    @ApiModelProperty(value = "手机号")
    private String mobile;
    @ApiModelProperty(value = "openId", hidden = true)
    @JsonIgnore
    private String openId;
}
