package com.seezoon.generator.constants;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * @author hdf
 */
class QueryTypeTest {

    @Test
    void parse() {
        boolean equals = QueryType.parse(QueryType.NOT_EQUAL.queryName()).equals(QueryType.NOT_EQUAL);
        assertTrue(equals);
    }
}