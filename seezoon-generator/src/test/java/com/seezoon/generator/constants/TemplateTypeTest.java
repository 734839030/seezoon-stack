package com.seezoon.generator.constants;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * @author hdf
 */
class TemplateTypeTest {

    @Test
    void ordinal() {
        assertTrue(TemplateType.CRUD.ordinal() == 0);
    }

    @Test
    void valueOf() {
        final boolean crud = TemplateType.valueOf("CRUD").equals(TemplateType.CRUD);
        assertTrue(crud);
    }
}