package com.seezoon.framework.component.file;

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
     * 文件id
     */
    private String id;

    /**
     * 文件全路径
     */
    private String fullUrl;
    /**
     * 相对路径
     */
    private String relativePath;
    /**
     * 原始文件名
     */
    private String originalFilename;

}
