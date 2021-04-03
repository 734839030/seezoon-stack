package com.seezoon.generator.constants;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * @author hdf
 */
class InputTypeTest {

    @Test
    void parse() {
        final boolean equals = InputType.valueOf(InputType.IMAGE.name()).equals(InputType.IMAGE);
        assertTrue(equals);
    }
}