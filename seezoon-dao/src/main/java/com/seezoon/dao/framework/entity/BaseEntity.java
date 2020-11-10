package com.seezoon.dao.framework.entity;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 数据库实体基类，框架默认字段，已实际表为准
 *
 * @author hdf
 */
@Data
public class BaseEntity<PK> {

    /**
     * 主键只支持单一主键，数值或者字符串，数据库表必须存在名为ID的主键列
     */
    @ApiModelProperty("*内置*-主键")
    private PK id;
    /**
     * 数据状态 {@link com.seezoon.dao.framework.constants.EntityStatus}
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
