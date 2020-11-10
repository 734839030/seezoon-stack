package com.seezoon.dao.framework.entity;

import io.swagger.annotations.ApiModelProperty;

/**
 * 分页条件
 *
 * @author hdf
 */
public class PageCondition extends QueryCondition {

    private static final int MAX_PAGE_SIZE = 1000;
    /**
     * 页码
     */
    @ApiModelProperty(value = "页码(分页请求必传)")
    private int page = 1;
    /**
     * 默认每页大小
     */
    @ApiModelProperty(value = "每页大小(分页请求必传)")
    private int pageSize = 20;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        if (pageSize > MAX_PAGE_SIZE) {
            pageSize = MAX_PAGE_SIZE;
        }
        this.pageSize = pageSize;
    }
}
