package com.seezoon.service.modules.${moduleName}.entity;

import com.seezoon.boot.common.entity.BaseEntity;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
<#if hasBigDecimal >
import java.math.BigDecimal;
</#if>
<#if hasDate >
import java.util.Date;
</#if>
<#if hasFileUpload >
import com.seezoon.service.modules.sys.dto.FileInfo;
import java.util.List;
</#if>
/**
 * ${menuName}
 * Copyright &copy; 2018 powered by huangdf, All rights reserved.
 * @author hdf ${.now}
 */
public class ${className} extends BaseEntity<${pkType}> {

   private static final long serialVersionUID = 1L;
<#list columnInfos as columnInfo>
	<#if columnInfo.javaFieldName != "id" && columnInfo.javaFieldName != "createBy" && columnInfo.javaFieldName != "createDate" && columnInfo.javaFieldName != "updateBy" && columnInfo.javaFieldName != "updateDate" && columnInfo.javaFieldName != "remarks">
    /**
     * ${columnInfo.columnComment}
     */
    <#if columnInfo.nullable! != "1">
    @NotNull
    <#if columnInfo.javaType == "String">
    @Length(min = 1, max = ${columnInfo.maxLength?c!255})
    </#if>
    <#else>
    <#if columnInfo.javaType == "String">
    @Length(max = ${columnInfo.maxLength?c!255})
    </#if>
    </#if>
    private ${columnInfo.javaType} ${columnInfo.javaFieldName};
    <#if columnInfo.inputType! == "file" || columnInfo.inputType! == "picture">
    private List<FileInfo> ${columnInfo.javaFieldName}Array;
    public List<FileInfo> get${columnInfo.javaFieldName ? cap_first}Array(){
        return ${columnInfo.javaFieldName}Array;
    }
    public void set${columnInfo.javaFieldName ? cap_first}Array(List<FileInfo> ${columnInfo.javaFieldName}Array){
        this.${columnInfo.javaFieldName}Array = ${columnInfo.javaFieldName}Array;
    }
    </#if>
	</#if>
</#list>
<#list columnInfos as columnInfo>
	<#if columnInfo.javaFieldName != "id" && columnInfo.javaFieldName != "createBy" && columnInfo.javaFieldName != "createDate" && columnInfo.javaFieldName != "updateBy" && columnInfo.javaFieldName != "updateDate" && columnInfo.javaFieldName != "remarks">
    public ${columnInfo.javaType} get${columnInfo.javaFieldName ? cap_first}(){
        return ${columnInfo.javaFieldName};
    }
    public void set${columnInfo.javaFieldName ? cap_first}(${columnInfo.javaType} ${columnInfo.javaFieldName}){
        this.${columnInfo.javaFieldName} = ${columnInfo.javaFieldName};
    }
	</#if>
</#list>
}