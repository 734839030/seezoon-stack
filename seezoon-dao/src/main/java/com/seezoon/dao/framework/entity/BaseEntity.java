package com.seezoon.dao.framework.entity;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 数据库实体基类，框架默认字段，已实际表为准
 *
 * @author hdf
 */
@Getter
@Setter
public class BaseEntity<PK> {

    /**
     * 主键只支持单一主键，数值或者字符串,名字可以用其他的,其他的代码生成器会覆写getId方法，方便使用。
     */
    @ApiModelProperty(value = "*内置*-主键")
    private PK id;
    /**
     * 数据状态 一般表建议不要这个字段，设计上没有软删除场景，如果有就用
     *
     * {@link com.seezoon.dao.framework.constants.EntityStatus}
     *
     * 使用Integer 写代码更方便
     *
     * 保存方法自动处理
     * </pre>
     */
    @ApiModelProperty("*内置*-状态")
    @Max(127)
    @Min(-128)
    private Integer status;
    /**
     * 保存方法自动处理
     */
    @ApiModelProperty("*内置*-创建人")
    @JsonIgnore
    private Integer createBy;
    /**
     * 保存方法自动处理
     */
    @ApiModelProperty("*内置*-创建时间")
    private Date createTime;
    /**
     * 更新方法自动处理
     */
    @ApiModelProperty("*内置*-更新人")
    @JsonIgnore
    private Integer updateBy;
    /**
     * 更新方法自动处理
     */
    @ApiModelProperty("*内置*-更新时间")
    private Date updateTime;

    @ApiModelProperty("*内置*-备注")
    @Size(max = 255)
    private String remarks;

}
