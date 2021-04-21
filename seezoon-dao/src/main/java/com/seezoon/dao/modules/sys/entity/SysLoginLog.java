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
 * 登录日志
 *
 * @author seezoon-generator 2021年4月18日 上午1:30:18
 */
@ApiModel(value = "登录日志")
@Getter
@Setter
@ToString
public class SysLoginLog extends BaseEntity<Integer> {

    @ApiModelProperty(value = "用户ID", required = true)
    @NotNull
    private Integer userId;

    @ApiModelProperty(value = "账号", required = true)
    @NotBlank
    @Size(max = 50)
    private String userName;

    @ApiModelProperty(value = "登录结果", required = true)
    @NotNull
    private Integer result;

    @ApiModelProperty(value = "登录时间", required = true)
    @NotNull
    private Date loginTime;

    @ApiModelProperty(value = "IP地址", required = true)
    @NotBlank
    @Size(max = 16)
    private String ip;

    @ApiModelProperty(value = "登录地区")
    private String area;

    @ApiModelProperty(value = "用户代理", required = true)
    @NotBlank
    @Size(max = 1000)
    private String userAgent;

    @ApiModelProperty(value = "设备名称")
    private String deviceName;

    @ApiModelProperty(value = "浏览器名称")
    private String browserName;

}