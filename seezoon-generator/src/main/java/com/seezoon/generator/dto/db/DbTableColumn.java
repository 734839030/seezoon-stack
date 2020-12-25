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

    /**
     * DB 列名称
     */
    private String name;
    /**
     * 列备注
     */
    private String comment;
    /**
     * 是否可以空
     */
    private Boolean nullable;
    /**
     * 列顺序
     */
    private Integer sort;
    /**
     * 如 varchar
     */
    private String dataType;
    /**
     * 字符长度
     */
    private Integer maxlength;
    /**
     * 列类型 eg:varchar(64)
     */
    private String columnType;
    /**
     * 主键类型 如PRI(主键) UNI(唯一) MUL(普通索引) {@link com.seezoon.generator.constants.db.ColumnKey}
     */
    private String columnKey;
    /**
     * 额外说明 如auto_increment
     */
    private String extra;

}
