package com.seezoon.dao.modules.sys.entity;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.seezoon.dao.framework.entity.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 生成案例
 *
 * @author seezoon-generator 2021年3月16日 上午1:16:00
 */
@ApiModel(value = "生成案例")
@Getter
@Setter
@ToString
public class SysDemo extends BaseEntity<Integer> {

    @ApiModelProperty(value = "文本", required = true)
    @NotBlank
    @Size(max = 50)
    private String inputText;

    @ApiModelProperty(value = "下拉", required = true)
    @NotBlank
    @Size(max = 10)
    private String inputSelect;

    @ApiModelProperty(value = "单选", required = true)
    @NotBlank
    @Size(max = 10)
    private String inputRadio;

    @ApiModelProperty(value = "复选", required = true)
    // @NotBlank
    @Size(max = 10)
    private String inputCheckbox;

    @ApiModelProperty(value = "文本域")
    private String inputTextarea;

    @ApiModelProperty(value = "日期", required = true)
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date inputDate;

    @ApiModelProperty(value = "整数")
    private Integer inputZhengshu;

    @ApiModelProperty(value = "小数")
    private Double inputXiaoshu;

    @ApiModelProperty(value = "富文本")
    private String richText;

    @ApiModelProperty(value = "图片")
    private String image;

    @ApiModelProperty(value = "文件")
    private String file;

}