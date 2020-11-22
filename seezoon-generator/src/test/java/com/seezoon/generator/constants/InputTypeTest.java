package com.seezoon.generator.constants;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * @author hdf
 */
class InputTypeTest {

    @Test
    void parse() {
        final boolean equals = InputType.parse(InputType.PICTURE.inputName()).equals(InputType.PICTURE);
        assertTrue(equals);
    }
}