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
 * 字典
 *
 * @author seezoon-generator 2020年12月26日 上午1:50:03
 */
@ApiModel
@Getter
@Setter
@ToString
public class SysDict extends BaseEntity<Integer> {

    @ApiModelProperty(value = "字典类型", required = true)
    @NotBlank
    @Size(max = 50)
    private String type;

    @ApiModelProperty(value = "编码", required = true)
    @NotBlank
    @Size(max = 50)
    private String code;

    @ApiModelProperty(value = "名称", required = true)
    @NotBlank
    @Size(max = 50)
    private String name;

    @ApiModelProperty(value = "排序", required = true)
    @NotNull
    private Integer sort;


}