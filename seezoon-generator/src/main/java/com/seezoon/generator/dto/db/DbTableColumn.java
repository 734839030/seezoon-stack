package com.seezoon.generator.dto.db;

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
    private String comment;
    private Boolean nullable;
    private Integer sort;
    private String dataType;
    private Integer maxlength;
    private String columnType;
    private String columnKey;
    private String extra;

}
