package com.seezoon.admin.modules.sys.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hdf
 */
@ApiModel(value = "保存用户信息")
@Getter
@Setter
public class UserAo {

    @ApiModelProperty(value = "姓名", required = true)
    @NotEmpty
    @Size(max = 50)
    private String name;

    @ApiModelProperty(value = "邮箱")
    @Size(max = 50)
    private String email;

    @ApiModelProperty(value = "头像")
    @Size(max = 100)
    private String photo;
}
