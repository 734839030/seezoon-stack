package com.seezoon.dao.framework.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;

/**
 * 该结构默认适合antd vue tree 组件
 *
 * @author hdf
 */
@Getter
@Builder
public class Tree {

    /**
     * 一般为主键
     */
    private Object key;
    private String title;
    private List<Tree> children;
    private boolean disabled;
    private boolean selectable;
    @JsonProperty("isLeaf")
    private boolean leaf;
}
