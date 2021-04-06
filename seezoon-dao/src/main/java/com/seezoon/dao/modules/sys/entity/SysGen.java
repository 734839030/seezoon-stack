package com.seezoon.dao.modules.sys.entity;

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
 * 代码生成
 *
 * @author seezoon-generator 2021年3月29日 下午11:27:05
 */
@ApiModel(value = "代码生成")
@Getter
@Setter
@ToString
public class SysGen extends BaseEntity<Integer> {

    @ApiModelProperty(value = "表名", required = true)
    @NotBlank
    @Size(max = 32)
    private String tableName;

    @ApiModelProperty(value = "菜单名", required = true)
    @NotBlank
    @Size(max = 50)
    private String menuName;

    @ApiModelProperty(value = "模块名", required = true)
    @NotBlank
    @Size(max = 10)
    private String moduleName;

    @ApiModelProperty(value = "功能模块", required = true)
    @NotBlank
    @Size(max = 20)
    private String functionName;

    @ApiModelProperty(value = "生成模板", required = true)
    @NotNull
    private Integer template;

    @ApiModelProperty(value = "类名", required = true)
    @NotBlank
    @Size(max = 50)
    private String className;

    @ApiModelProperty(value = "字段信息", required = true)
    @NotBlank
    @Size(max = 65535)
    private String columns;

}