package com.seezoon.generator.io;

import org.junit.jupiter.api.Test;

import com.seezoon.generator.constants.db.ColumnDataType;
import com.seezoon.generator.plan.ColumnPlan;

/**
 * @author hdf
 */
class FreeMarkerRenderTest {

    @Test
    void testBooleanIs() {

        ColumnPlan columnPlan = ColumnPlan.builder().defaultField(false).build();
        System.out.println(FreeMarkerRender
            .renderStringTemplate("${defaultField?string('true','false')}<#if defaultField>12</#if>", columnPlan));
    }

    @Test
    void testEnum() {
        ColumnPlan columnPlan = ColumnPlan.builder().dataType(ColumnDataType.VARCHAR).build();
        System.out.println(FreeMarkerRender.renderStringTemplate("${dataType.javaType()}", columnPlan));
    }
}