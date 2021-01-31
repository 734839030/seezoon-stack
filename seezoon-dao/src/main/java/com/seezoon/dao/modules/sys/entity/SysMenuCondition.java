package com.seezoon.dao.modules.sys.entity;

import com.seezoon.dao.framework.entity.PageCondition;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 菜单管理
 *
 * @author seezoon-generator 2021年1月31日 上午12:21:33
 */
@Getter
@Setter
@ToString
public class SysMenuCondition extends PageCondition {

    /**
     * 上级
     */
    @ApiModelProperty(value = "上级")
    private Integer parentId;
    /**
     * 所有父级编号
     */
    @ApiModelProperty(value = "所有上级,like查询eg:/a/")
    private String parentIds;
    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;

}