package com.seezoon.dao.modules.sys.entity;

import java.util.Date;

import com.seezoon.dao.framework.entity.PageCondition;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 字典
 * @author seezoon-generator 2020年12月2日 下午11:35:26
 */
@Getter
@Setter
@ToString
public class SysDictCondition extends PageCondition {

    /**
     * 字典类型
     */
    private String type;

}