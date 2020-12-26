package com.seezoon.dao.modules.${moduleName}.entity;

<#if importDate>
import java.util.Date;
</#if>

import com.seezoon.dao.framework.entity.PageCondition;
<#if sortable>
import com.seezoon.dao.framework.sort.annotation.SortField;
</#if>
<#if hasSearch>
import io.swagger.annotations.ApiModelProperty;
</#if>
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
<#if sortable>
<#assign firstItem = true>
@SortField({<#list columnPlans as columnPlan><#if columnPlan.sortable>${firstItem?string("",",")}"${columnPlan.javaFieldName}:${columnPlan.dbColumnName}"<#assign firstItem = false></#if></#list>})
</#if>
public class ${className}Condition extends PageCondition {

    <#list columnPlans as columnPlan>
        <#if columnPlan.search>
    /**
     * ${columnPlan.fieldName!}
     */
    @ApiModelProperty(value = "${columnPlan.fieldName!}")
    private ${columnPlan.dataType.javaType()} ${columnPlan.javaFieldName};
        </#if>
    </#list>

}