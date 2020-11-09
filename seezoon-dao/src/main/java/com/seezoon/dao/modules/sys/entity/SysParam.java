package com.seezoon.dao.modules.sys.entity;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.seezoon.dao.framework.entity.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author hdf
 */
@ApiModel
@Getter
@Setter
@ToString
public class SysParam extends BaseEntity<Integer> {

    @ApiModelProperty(value = "参数名称 1~50", required = true)
    @NotEmpty
    @Length(min = 1, max = 50)
    private String name;

    @ApiModelProperty(value = "参数唯一Key 1~50", required = true)
    @NotEmpty
    @Length(min = 1, max = 50)
    private String paramKey;

    @ApiModelProperty(value = "参数值 1~100", required = true)
    @NotEmpty
    @Length(min = 1, max = 100)
    private String paramValue;

}