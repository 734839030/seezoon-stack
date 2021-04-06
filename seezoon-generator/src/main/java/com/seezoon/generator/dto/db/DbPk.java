package com.seezoon.generator.dto.db;

import lombok.Getter;
import lombok.Setter;

/**
 * @author hdf
 */
@Getter
@Setter
public class DbPk {
    /**
     * DB 列名称
     */
    private String name;
    /**
     * 如 varchar
     */
    private String dataType;

    /**
     * 自增
     */
    private Boolean autoIncrement;
}
