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

    private String name;
    private String paramKey;
    private Integer status;

}