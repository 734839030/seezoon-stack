package com.seezoon.dao.modules.sys.entity;

import com.seezoon.dao.framework.entity.PageCondition;
import com.seezoon.dao.framework.sort.annotation.SortField;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author hdf
 */
@Getter
@Setter
@ToString
@SortField({"updateTime:update_time"})
public class SysParamCondition extends PageCondition {

    /**
     * 名字
     */
    @ApiModelProperty(value = "参数名称")
    private String name;
    /**
     * 唯一键
     */
    @ApiModelProperty(value = "唯一键")
    private String paramKey;

    @ApiModelProperty(value = "状态")
    private Integer status;

}