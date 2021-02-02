package com.seezoon.dao.framework.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;

/**
 * 该结构默认适合antd vue tree 及tree select 组件
 *
 * @author hdf
 */
@Getter
@Builder
public class Tree {

    /**
     * 一般为主键 tree 组件元素采用随机值，以免节点状态缓存，比如展开后，重新加载数据后，节点状态不变问题
     */
    private Object key;
    private String title;
    private List<Tree> children;
    private boolean disabled;
    private boolean selectable;
    private boolean checkable;
    @JsonProperty("isLeaf")
    private boolean leaf;
    private Object value;

}
