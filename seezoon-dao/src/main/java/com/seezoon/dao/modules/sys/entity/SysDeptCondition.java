package com.seezoon.dao.modules.sys.entity;

import com.seezoon.dao.framework.entity.PageCondition;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 组织机构
 *
 * @author seezoon-generator 2021年1月12日 下午10:54:44
 */
@Getter
@Setter
@ToString
public class SysDeptCondition extends PageCondition {

    /**
     * 父部门
     */
    @ApiModelProperty(value = "父部门")
    private Integer parentId;

    /**
     * 部门名称
     */
    @ApiModelProperty(value = "部门名称")
    private String name;

    /**
     * like 查询 /a/
     */
    @ApiModelProperty(value = "父部门")
    private String parentIds;
}