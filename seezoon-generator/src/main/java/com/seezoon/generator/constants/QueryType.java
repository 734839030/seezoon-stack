package com.seezoon.generator.constants;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.Assert;

/**
 * 字段的查询方式
 *
 * @author hdf
 */
public enum QueryType {

    EQUAL("="),

    NOT_EQUAL("!="),

    GREATER_EQUAL(">="),

    GREATER(">"),

    LESS_EQUAL("<="),

    LESS("<"),

    BETWEEN("between"),

    LIKE("like"),

    LEFT_LIKE("left_like"),

    RIGHT_LIKE("left_like"),

    ;

    private static final Map<String, QueryType> queryTypes = new HashMap<>();

    static {
        Arrays.stream(QueryType.values()).forEach((v) -> {
            queryTypes.put(v.queryName, v);
        });
    }

    /**
     * 名称
     */
    private String queryName;

    QueryType(String queryName) {
        this.queryName = queryName;
    }

    public static QueryType parse(String queryName) {
        QueryType queryType = queryTypes.get(queryName);
        Assert.isTrue(null != queryType,
            String.format("queryName[%s] not support,pls supplement enum QueryType", queryName));
        return queryType;
    }

    public String queryName() {
        return queryName;
    }

}
