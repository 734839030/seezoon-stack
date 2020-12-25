package com.seezoon.generator.io;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.seezoon.generator.constants.db.ColumnDataType;
import com.seezoon.generator.plan.ColumnPlan;
import com.seezoon.generator.plan.TablePlan;

/**
 * 枚举需要点方法，见下方测试
 *
 * @see <a>http://freemarker.foofun.cn/dgui_quickstart_template.html</a>
 *
 * @author hdf
 */
class FreeMarkerRenderTest {

    @Test
    void testBooleanIs() {
        ColumnPlan columnPlan = ColumnPlan.builder().list(false).build();
        // http://freemarker.foofun.cn/dgui_quickstart_template.html#autoid_7
        System.out.println(
            FreeMarkerRender.renderStringTemplate("${list?string('true','false')}<#if list>12</#if>", columnPlan));
    }

    @Test
    void testEnum() {
        ColumnPlan columnPlan = ColumnPlan.builder().dataType(ColumnDataType.VARCHAR).build();
        System.out.println(FreeMarkerRender.renderStringTemplate("${dataType.javaType()}", columnPlan));
    }

    @Test
    void testStaticField() {
        TablePlan tablePlan = new TablePlan();
        System.out.println(FreeMarkerRender.renderStringTemplate("${defaultTableAlias}", tablePlan));
    }

    /**
     * 特殊字符
     *
     * @see <a>http://freemarker.foofun.cn/ref_directive_noparse.html</a>
     */
    @Test
    void testSpecialSymbol() {
        TablePlan tablePlan = new TablePlan();
        tablePlan.setModuleName("setModuleName");
        System.out.println(FreeMarkerRender.renderStringTemplate("<#noparse>#</#noparse>{${moduleName}}", tablePlan));
        System.out.println(FreeMarkerRender.renderStringTemplate("${'#'}{${moduleName}}", tablePlan));
        System.out.println(FreeMarkerRender.renderStringTemplate("${'#'}{item.${moduleName}}", tablePlan));
        Assertions.assertThrows(RuntimeException.class, () -> {
            System.out.println(FreeMarkerRender.renderStringTemplate("#{${moduleName}}", tablePlan));
        });
    }
}