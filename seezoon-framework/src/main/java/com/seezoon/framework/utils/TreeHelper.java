package com.seezoon.framework.utils;

import java.util.Objects;

import org.springframework.util.Assert;

/**
 * @author hdf
 */
public class TreeHelper {

    /**
     * 分隔符
     */
    public static final String SEPARATOR = "/";
    public static final Integer DEFAULT_PARENT_ID = 0;
    public static final String DEFAULT_PARENT_IDS = "/0/";

    /**
     * 获取当前parentIds
     *
     * @param id
     *            当前节点ID
     * @param parentId
     *            当前父节点
     * @param parentIds
     *            当前父节点的所有父节点
     * @return
     */
    public static String getCurrentParentIds(Object id, Object parentId, String parentIds) {
        Assert.notNull(parentId, "parentId must not be empty");
        Assert.hasText(parentIds, "parentIds must not be empty");
        // 检查不能设置自己的子节点
        if (null != id && parentIds.contains(SEPARATOR + id + SEPARATOR)) {
            throw new IllegalArgumentException("父节点不能为当前节点子节点");
        }
        if (Objects.equals(id, parentId)) {
            throw new IllegalArgumentException("不能设置父节点是自己");
        }

        return parentIds + parentId + SEPARATOR;
    }

    /**
     * 查询时候用
     *
     * @param parentId
     * @return
     */
    public static String getQueryParentIds(Object parentId) {
        Assert.notNull(parentId, "parentId must not be empty");
        return SEPARATOR + parentId + SEPARATOR;
    }
}
