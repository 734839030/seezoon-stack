package com.seezoon.generator.constants;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * @author hdf
 */
class QueryTypeTest {

    @Test
    void parse() {
        boolean equals = QueryType.valueOf("NOT_EQUAL").equals(QueryType.NOT_EQUAL);
        assertTrue(equals);
    }

    @Test
    void name() {
        assertTrue(QueryType.NOT_EQUAL.name().equals("NOT_EQUAL"));
    }
}