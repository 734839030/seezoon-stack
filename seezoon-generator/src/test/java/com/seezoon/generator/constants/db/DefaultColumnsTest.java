package com.seezoon.generator.constants.db;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * @author hdf
 */
class DefaultColumnsTest {

    @Test
    void name() {
        assertTrue(DefaultColumns.id.name().equals("id"));
    }
}