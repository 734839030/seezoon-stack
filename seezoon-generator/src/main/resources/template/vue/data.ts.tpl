<#if hasDictWidget>
import { Dict } from '/@/api/sys/model/dictModel';
import { dictArray2Map, getDict } from '/@/api/sys';

// 提示: 字典的value 类型需要和后台实际返回的数据类型一致，不然在数据回显的时候，无法自自动选中，校验也会不生效
<#list columnPlans as columnPlan>
 <#if columnPlan.dictField>
    <#if columnPlan.dictType?? && (columnPlan.dictType?length > 0) >
export const ${columnPlan.javaFieldName}Dicts: Dict[] = getDict('${columnPlan.dictType}}'<#if !columnPlan.stringType>, true</#if>);
export const ${columnPlan.javaFieldName}DictsMap = dictArray2Map(${columnPlan.javaFieldName}Dicts);
    <#else>
export const ${columnPlan.javaFieldName}Dicts: Dict[] = [
  { value: <#if columnPlan.stringType>'10'<#else>10</#if>, label: '请补充' }
];
export const ${columnPlan.javaFieldName}DictsMap = dictArray2Map(${columnPlan.javaFieldName}Dicts);
    </#if>
 </#if>

</#list>
</#if>
