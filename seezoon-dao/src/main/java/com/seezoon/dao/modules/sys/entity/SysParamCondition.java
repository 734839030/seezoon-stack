package com.seezoon.dao.modules.sys.entity;

import com.seezoon.dao.framework.entity.PageCondition;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author hdf
 */
@Getter
@Setter
@ToString
public class SysParamCondition extends PageCondition {

    /**
     * 名字
     */
    private String name;
    /**
     * 唯一键
     */
    private String paramKey;
    /**
     * 状态
     */
    private Integer status;

}