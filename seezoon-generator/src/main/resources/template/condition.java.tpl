package com.seezoon.dao.modules.${moduleName}.entity;

<#if importDate>
import java.util.Date;
</#if>

<#list columnPlans as columnPlan>
  <#if columnPlan.dataType.javaType() == "Date" && columnPlan.search>
import org.springframework.format.annotation.DateTimeFormat;
// DateTimeFormat 针对RequestBody 接收无效，实际会使用JsonFormat
// DateTimeFormat 针对form表单时间格式化有效
import com.fasterxml.jackson.annotation.JsonFormat;
  <#break>
  </#if>
</#list>

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
@SortField({<#list columnPlans as columnPlan><#if columnPlan.sortable>${firstItem?string("",",")}"${columnPlan.javaFieldName}:${defaultTableAliasPrefix}${columnPlan.dbColumnName}"<#assign firstItem = false></#if></#list>})
</#if>
public class ${className}Condition extends PageCondition {

    <#list columnPlans as columnPlan>
        <#if columnPlan.search>
    /**
     * ${columnPlan.fieldName!}
     */
    @ApiModelProperty(value = "${columnPlan.fieldName!}")
          <#if columnPlan.inputType.name() == "DATE">
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
          <#elseif columnPlan.inputType.name() == "DATETIME">
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
          </#if>
    private ${columnPlan.dataType.javaType()} ${columnPlan.javaFieldName};
        <#if columnPlan.multiple>
        // 辅助接收多选
    @ApiModelProperty(value = "${columnPlan.fieldName!}")
    private ${columnPlan.dataType.javaType()}[] ${columnPlan.javaFieldName}Array;
        </#if>
        </#if>
    </#list>

}