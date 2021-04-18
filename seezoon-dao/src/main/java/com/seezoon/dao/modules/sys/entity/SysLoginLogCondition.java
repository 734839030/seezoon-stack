package com.seezoon.dao.modules.sys.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.seezoon.dao.framework.entity.PageCondition;
import com.seezoon.dao.framework.sort.annotation.SortField;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 登录日志
 *
 * @author seezoon-generator 2021年4月18日 上午1:30:18
 */
@Getter
@Setter
@ToString
@SortField({"userId:t.user_id", "userName:t.user_name", "loginTime:t.login_time", "ip:t.ip"})
public class SysLoginLogCondition extends PageCondition {

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    private Integer userId;
    /**
     * 账号
     */
    @ApiModelProperty(value = "账号")
    private String userName;
    /**
     * 登录结果
     */
    @ApiModelProperty(value = "登录结果")
    private Integer result;
    /**
     * 登录时间
     */
    @ApiModelProperty(value = "登录时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date loginTime;
    /**
     * IP地址
     */
    @ApiModelProperty(value = "IP地址")
    private String ip;

}