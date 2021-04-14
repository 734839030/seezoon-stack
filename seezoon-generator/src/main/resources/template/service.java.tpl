package com.seezoon.admin.modules.${moduleName}.service;

<#list columnPlans as columnPlan>
  <#if columnPlan.uniqueFieldCheck && columnPlan.stringType>
import javax.validation.constraints.NotBlank;
    <#break>
  </#if>
</#list>
<#list columnPlans as columnPlan>
  <#if columnPlan.uniqueFieldCheck && !columnPlan.stringType>
import javax.validation.constraints.NotNull;
    <#break>
  </#if>
</#list>

import org.springframework.stereotype.Service;

import com.seezoon.admin.framework.service.AbstractCrudService;
import com.seezoon.dao.modules.${moduleName}.${className}Dao;
import com.seezoon.dao.modules.${moduleName}.entity.${className};
<#list columnPlans as columnPlan>
  <#if columnPlan.uniqueFieldCheck>
import com.seezoon.dao.modules.${moduleName}.entity.${className}Condition;
    <#break>
  </#if>
</#list>

/**
 * ${menuName!}
 * @author seezoon-generator ${.now}
 */
@Service
public class ${className}Service extends AbstractCrudService<${className}Dao, ${className}, ${pkPlan.dataType.javaType()}> {
    <#list columnPlans as columnPlan>
      <#if columnPlan.uniqueFieldCheck>

    @Transactional(readOnly = true)
    public SysParam findBy${columnPlan.javaFieldName?cap_first}(${columnPlan.stringType?string("@NotBlank","@NotNull")} ${columnPlan.dataType.javaType()} ${columnPlan.javaFieldName}) {
        ${className}Condition ${className?uncap_first}Condition = new SysParamCondition();
        ${className?uncap_first}Condition.set${columnPlan.javaFieldName?cap_first}(${columnPlan.javaFieldName});
        return this.findOne(${className?uncap_first}Condition);
    }
      </#if>
    </#list>
}
