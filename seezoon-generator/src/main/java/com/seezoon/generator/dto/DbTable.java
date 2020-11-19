package com.seezoon.generator.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 表基本信息
 *
 * @author hdf
 */
@Getter
@Setter
public class DbTable {

    /**
     * 表名
     */
    private String name;
    /**
     * 备注
     */
    private String comment;

}
