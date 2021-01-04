package com.seezoon.dao.modules.sys.entity;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.seezoon.dao.framework.entity.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 文件
 *
 * @author seezoon-generator 2021年1月2日 上午1:04:41
 */
@ApiModel
@Getter
@Setter
@ToString
public class SysFile extends BaseEntity<Integer> {

    @ApiModelProperty(value = "名称", required = true)
    @NotBlank
    @Size(max = 200)
    private String name;

    @ApiModelProperty(value = "文件类型", required = true)
    @NotBlank
    @Size(max = 100)
    private String contentType;

    @ApiModelProperty(value = "文件大小B", required = true)
    @NotNull
    private Integer fileSize;

    @ApiModelProperty(value = "相对路径", required = true)
    @NotBlank
    @Size(max = 200)
    private String relativePath;


}