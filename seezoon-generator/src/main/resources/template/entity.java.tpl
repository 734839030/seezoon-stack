package com.seezoon.dao.modules.${moduleName}.entity;

<#if importBigDecimal>
import java.math.BigDecimal;
</#if>
<#if importDate>
import java.util.Date;
</#if>

<#list columnPlans as columnPlan>
   <#if columnPlan.multiple>
import java.util.Arrays;
import com.seezoon.dao.framework.constants.Constants;
import org.apache.commons.lang3.StringUtils;
import javax.validation.constraints.NotEmpty;
       <#break>
   </#if>
</#list>

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

<#if importDate>
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
</#if>
import com.seezoon.dao.framework.entity.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * ${menuName!}
 *
 * @author seezoon-generator ${.now}
 */
@ApiModel(value = "${menuName!}")
@Getter
@Setter
@ToString
public class ${className} extends BaseEntity<${pkPlan.dataType.javaType()}> {

<#list columnPlans as columnPlan>
   <#if !columnPlan.defaultField>
    @ApiModelProperty(value = "${columnPlan.fieldName!}"<#if !columnPlan.nullable && !columnPlan.multiple>, required = true</#if>)
      <#if !columnPlan.nullable && !columnPlan.multiple>
        <#if columnPlan.stringType>
    @NotBlank
        <#else>
    @NotNull
        </#if>
      </#if>
      <#if columnPlan.stringType>
    @Size(max = ${columnPlan.maxLength?c})
      </#if>
        <#if columnPlan.inputType.name() == "DATE">
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
        <#elseif columnPlan.inputType.name() == "DATETIME">
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        </#if>
    private ${columnPlan.dataType.javaType()} ${columnPlan.javaFieldName};

   </#if>
</#list>
<#list columnPlans as columnPlan>
    <#if columnPlan.multiple>
    // 辅助接收多选
    @ApiModelProperty(value = "${columnPlan.fieldName!}"<#if !columnPlan.nullable>, required = true</#if>)
    <#if !columnPlan.nullable>
    @NotEmpty
    </#if>
    private Object[] ${columnPlan.javaFieldName}Array;

    private ${columnPlan.dataType.javaType()} get${columnPlan.javaFieldName?cap_first}() {
        if (null != ${columnPlan.javaFieldName}Array) {
            return StringUtils.join(${columnPlan.javaFieldName}Array, Constants.COMMA);
        }
        return ${columnPlan.javaFieldName};
    }

    public Object[] get${columnPlan.javaFieldName?cap_first}Array() {
        if (StringUtils.isNotEmpty(${columnPlan.javaFieldName})) {
            return Arrays.stream(StringUtils.split(${columnPlan.javaFieldName}, Constants.COMMA)).map(v -> ${columnPlan.dataType.javaType()}.valueOf(v))
                .toArray();
        }
        return ${columnPlan.javaFieldName}Array;
    }
    </#if>
</#list>


<#if !pkPlan.defaultJavaPkName>
    @Override
    public ${pkPlan.dataType.javaType()} getId() {
        return ${pkPlan.javaFieldName};
    }

    @Override
    public void setId(${pkPlan.dataType.javaType()} ${pkPlan.javaFieldName}) {
        this.setId(${pkPlan.javaFieldName});
        this.${pkPlan.javaFieldName} = ${pkPlan.javaFieldName};
    }
</#if>
}