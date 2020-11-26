<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="com.seezoon.service.modules.${moduleName}.dao.${className}Dao" >
  <resultMap id="BaseResultMap" type="com.seezoon.service.modules.${moduleName}.entity.${className}" >
<#list columnInfos as columnInfo>
    <${(columnInfo.columnKey == "PRI") ? string("id","result")} column="${columnInfo.dbColumnName}" property="${columnInfo.javaFieldName}" jdbcType="${columnInfo.jdbcType}" />
</#list>
  </resultMap>
  
  <sql id="Base_Column_List" >
  <#assign notFirst = false>
  <#list columnInfos as columnInfo><#if columnInfo.jdbcType !='LONGVARCHAR'><#if notFirst>,</#if>${columnInfo.dbColumnName}<#assign notFirst = true></#if></#list>
  </sql>
  <#if hasBlob>
  <sql id="Blob_Column_List" >
  <#assign notFirst = false>
  <#list columnInfos as columnInfo><#if columnInfo.jdbcType =='LONGVARCHAR'><#if notFirst>,</#if>${columnInfo.dbColumnName}<#assign notFirst = true></#if></#list>
  </sql>
  </#if>
  <select id="selectByPrimaryKey" resultMap="BaseResultMap" parameterType="${pkType}" >
    select 
    <include refid="Base_Column_List" />
    <#if hasBlob>
    ,<include refid="Blob_Column_List" />
    </#if>
    from ${tableName}
    where id = ${r'#{id}'}
  </select>
  <select id="findList" resultMap="BaseResultMap" parameterType="com.seezoon.service.modules.${moduleName}.entity.${className}" >
    select 
    <include refid="Base_Column_List" />
    from ${tableName}
<#if hasSearch>
    <where>
<#list columnInfos as columnInfo>
	<#if columnInfo.search! == "1">
    <if test="${(columnInfo.javaType == "String")?string("${columnInfo.javaFieldName} !=null and ${columnInfo.javaFieldName}!=''","${columnInfo.javaFieldName} !=null")}">
    		<#if columnInfo.queryType! == "=">
    and ${columnInfo.dbColumnName} = ${"#"}{${columnInfo.javaFieldName}}
        <#elseif columnInfo.queryType! == "!=">
    and ${columnInfo.dbColumnName} != ${"#"}{${columnInfo.javaFieldName}}
    		<#elseif columnInfo.queryType! == ">=">
    and ${columnInfo.dbColumnName} >= ${"#"}{${columnInfo.javaFieldName}}
    		<#elseif columnInfo.queryType! == ">">
    and ${columnInfo.dbColumnName} > ${"#"}{${columnInfo.javaFieldName}}
    	    <#elseif columnInfo.queryType! == "<=">
    and <![CDATA[  ${columnInfo.dbColumnName} <= ${"#"}{${columnInfo.javaFieldName}} ]]>
    		<#elseif columnInfo.queryType! == "<">
    and <![CDATA[  ${columnInfo.dbColumnName} < ${"#"}{${columnInfo.javaFieldName}} ]]>
    		<#elseif columnInfo.queryType! == "between">
    and ${columnInfo.dbColumnName} between ${"#"}{${columnInfo.javaFieldName}} and ${"#"}{${columnInfo.javaFieldName}}
    		<#elseif columnInfo.queryType! == "like">
    and ${columnInfo.dbColumnName} like concat("%",${"#"}{${columnInfo.javaFieldName}},"%")
    		<#elseif columnInfo.queryType! == "left like">
    and ${columnInfo.dbColumnName} like concat("%",${"#"}{${columnInfo.javaFieldName}})
    		<#elseif columnInfo.queryType! == "right like">
    and ${columnInfo.dbColumnName} like concat(${"#"}{${columnInfo.javaFieldName}},"%")
    		<#else>
    	and ${columnInfo.dbColumnName} = ${"#"}{${columnInfo.javaFieldName}}
    		</#if>
    </if>
	</#if>
</#list>
	${r'${dsf}'}
    </where>
</#if>
	<choose>
    <when test="sortField != null and sortField != '' and direction != null and direction !=''">
    order by ${r'${sortField} ${direction}'}
    </when>
    <otherwise>
   <!-- 默认排序设置 -->
   order by create_date desc
    </otherwise>
    </choose>
  </select>
  <delete id="deleteByPrimaryKey" >
    delete from ${tableName}
    where id = ${r'#{id}'} ${r'${dsf}'}
  </delete>
  <#assign notFirst = false>
  <insert id="insert" parameterType="com.seezoon.service.modules.${moduleName}.entity.${className}" >
    insert into ${tableName} (<#list columnInfos as columnInfo><#if columnInfo.insert! ="1"><#if notFirst>,</#if>${columnInfo.dbColumnName}<#assign notFirst = true></#if></#list>)
    <#assign notFirst = false>
    values (<#list columnInfos as columnInfo><#if columnInfo.insert! ="1"><#if notFirst>,</#if>${"#"}{${columnInfo.javaFieldName}}<#assign notFirst = true></#if></#list>)
  </insert>
  <update id="updateByPrimaryKeySelective" parameterType="com.seezoon.service.modules.${moduleName}.entity.${className}" >
    update ${tableName}
    <set>
      <#list columnInfos as columnInfo>
      <#if columnInfo.update! ="1">
      <if test="${columnInfo.javaFieldName} != null" >
        ${columnInfo.dbColumnName} = ${"#"}{${columnInfo.javaFieldName}},
      </if>
      </#if>
      </#list>
    </set>
    where id = ${r'#{id}'} ${r'${dsf}'}
  </update>
  <update id="updateByPrimaryKey" parameterType="com.seezoon.service.modules.${moduleName}.entity.${className}" >
    update ${tableName}
    set 
    	<#assign notFirst = false>
      <#list columnInfos as columnInfo>
      <#if columnInfo.update! ="1">
        <#if notFirst>,</#if>${columnInfo.dbColumnName} = ${"#"}{${columnInfo.javaFieldName}}<#assign notFirst = true>
      </#if>
      </#list>
    where id = ${r'#{id}'} ${r'${dsf}'}
  </update>
</mapper>