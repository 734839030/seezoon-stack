package com.seezoon.dao.modules.sys.entity;

import java.util.Date;

import com.seezoon.dao.framework.entity.PageCondition;
import com.seezoon.dao.framework.sort.annotation.SortField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 代码生成
 * @author seezoon-generator 2021年3月29日 下午11:27:05
 */
@Getter
@Setter
@ToString
@SortField({"tableName:t.table_name"})
public class SysGenCondition extends PageCondition {

    /**
     * 表名
     */
    @ApiModelProperty(value = "表名")
    private String tableName;

}