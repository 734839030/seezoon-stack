package com.seezoon.framework.component.file;

import com.seezoon.framework.utils.IdGen;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 文件信息
 *
 * @author hdf 2018年4月15日
 */
@ApiModel
@AllArgsConstructor
@Getter
@Setter
public class FileInfo {

    /**
     * 文件全路径
     */
    @ApiModelProperty("全路径")
    private String url;
    /**
     * 相对路径
     */
    @ApiModelProperty("相对路径")
    private String relativePath;
    /**
     * 原始文件名
     */
    @ApiModelProperty("原始文件名")
    private String name;

    /**
     * antd vue 默认上传完成状态
     */
    @ApiModelProperty("antdv 要求")
    public String getStatus() {
        return "done";
    }

    /**
     * antd vue 组件key
     *
     * @return
     */
    @ApiModelProperty("antdv 要求")
    public String getUid() {
        return IdGen.uuid();
    }
}
