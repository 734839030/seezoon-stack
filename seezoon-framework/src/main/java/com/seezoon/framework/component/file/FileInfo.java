package com.seezoon.framework.component.file;

import com.seezoon.framework.utils.IdGen;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 文件信息
 *
 * @author hdf 2018年4月15日
 */
@AllArgsConstructor
@Getter
@Setter
public class FileInfo {

    /**
     * 文件全路径
     */
    private String url;
    /**
     * 相对路径
     */
    private String relativePath;
    /**
     * 原始文件名
     */
    private String name;

    /**
     * antd vue 默认上传完成状态
     */
    public String getStatus() {
        return "done";
    }

    /**
     * antd vue 组件key
     *
     * @return
     */
    public String getUid() {
        return IdGen.uuid();
    }
}
