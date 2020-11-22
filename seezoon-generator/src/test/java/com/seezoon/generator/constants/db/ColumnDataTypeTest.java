package com.seezoon.generator.constants.db;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * @author hdf
 */
class ColumnDataTypeTest {

    @Test
    void parse() {
        assertTrue(ColumnDataType.parse("varchar").javaType().equals(String.class.getSimpleName()));
    }
}