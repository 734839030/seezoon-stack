package com.seezoon.generator.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 表字段基本信息
 *
 * @author hdf
 */

@Getter
@Setter
public class DbTableColumn {

    private String name;
    private Boolean nullable;
    private Integer sort;
    private String comment;
    private String dataType;
    private Long maxlength;
    private String columnType;
    private String columnKey;
    private String extra;

}
