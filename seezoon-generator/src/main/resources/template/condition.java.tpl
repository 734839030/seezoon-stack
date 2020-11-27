package com.seezoon.dao.modules.${moduleName}.entity;

<#if importDate>
import java.util.Date;
</#if>

import com.seezoon.dao.framework.entity.PageCondition;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * ${menuName!}
 * @author seezoon-generator ${.now}
 */
@Getter
@Setter
@ToString
public class ${className}Condition extends PageCondition {

    <#list columnPlans as columnPlan>
        <#if columnPlan.search>
    /**
     * ${columnPlan.fieldName!}
     */
    private ${columnPlan.dataType.javaType()} ${columnPlan.javaFieldName};
        </#if>
    </#list>

}