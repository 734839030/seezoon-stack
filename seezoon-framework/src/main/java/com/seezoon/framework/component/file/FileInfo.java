package com.seezoon.framework.component.file;

/**
 * 文件信息
 *
 * @author hdf 2018年4月15日
 */
public class FileInfo {

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

    public FileInfo(String fullUrl, String relativePath, String originalFilename) {
        super();
        this.fullUrl = fullUrl;
        this.relativePath = relativePath;
        this.originalFilename = originalFilename;
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public void setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

}
