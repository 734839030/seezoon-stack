package com.seezoon.dao.modules.sys.entity;

import com.seezoon.dao.framework.entity.PageCondition;
import com.seezoon.dao.framework.sort.annotation.SortField;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 字典
 *
 * @author seezoon-generator 2020年12月26日 下午3:45:28
 */
@Getter
@Setter
@ToString
@SortField({"updateTime:update_time"})
public class SysDictCondition extends PageCondition {

    /**
     * 字典类型
     */
    @ApiModelProperty(value = "字典类型")
    private String type;

    @ApiModelProperty(value = "编码")
    private String code;
}