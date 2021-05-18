package com.seezoon.dao.framework.entity;

/**
 * 查询基类
 *
 * @author hdf
 */
public abstract class AbstractQueryCondition {

    /**
     * 重载的方法当传null 语法错误问题
     */
    public static final EmptyQueryCondition EMPTY = null;

    private static final class EmptyQueryCondition extends AbstractQueryCondition {

    }
}
