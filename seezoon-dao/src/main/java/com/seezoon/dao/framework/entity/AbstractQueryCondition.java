package com.seezoon.dao.framework.entity;

/**
 * 查询基类
 *
 * @author hdf
 */
public abstract class AbstractQueryCondition {

    public static final EmptyQueryCondition EMPTY = new EmptyQueryCondition();

    private static final class EmptyQueryCondition extends AbstractQueryCondition {

    }
}
