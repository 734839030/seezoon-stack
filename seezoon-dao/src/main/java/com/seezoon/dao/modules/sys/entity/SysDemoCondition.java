package com.seezoon.dao.modules.sys.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.seezoon.dao.framework.entity.PageCondition;
import com.seezoon.dao.framework.sort.annotation.SortField;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 生成案例
 *
 * @author seezoon-generator 2021年3月16日 上午1:16:00
 */
@Getter
@Setter
@ToString
@SortField({"inputText:t.input_text"})
public class SysDemoCondition extends PageCondition {

    /**
     * 文本
     */
    @ApiModelProperty(value = "文本")
    private String inputText;
    /**
     * 单选
     */
    @ApiModelProperty(value = "单选")
    private String inputRadio;

    @ApiModelProperty(value = "多选")
    private String inputCheckbox;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;

}