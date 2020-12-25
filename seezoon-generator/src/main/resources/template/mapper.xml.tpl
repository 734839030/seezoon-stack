<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.seezoon.dao.modules.${moduleName}.${className}Dao">
    <resultMap id="BaseResultMap" type="com.seezoon.dao.modules.${moduleName}.entity.${className}">
        <#list columnPlans as columnPlan>
        <${(columnPlan.dbColumnName == pkPlan.dbColumnName)?string("id","result")} column="${columnPlan.dbColumnName}" jdbcType="${columnPlan.dataType.jdbcType()}" property="${columnPlan.javaFieldName}"/>
        </#list>
    </resultMap>

    <sql id="Base_Column_List">
    <#assign firstItem = true>
    <#list columnPlans as columnPlan><#if !columnPlan.blobType>${firstItem?string("",",")}${defaultTableAliasPrefix}${columnPlan.javaFieldName}<#assign firstItem = false></#if></#list>
    </sql>

    <#if hasBlob>
    <sql id="Blob_Column_List" >
    <#assign firstItem = true>
    <#list columnPlans as columnPlan><#if columnPlan.blobType>${firstItem?string("",",")}${defaultTableAliasPrefix}${columnPlan.javaFieldName}<#assign firstItem = false></#if></#list>
    </sql>
    </#if>
    <sql id="Query_Table" >
    ${tableName} ${defaultTableAlias}
    </sql>

    <select id="selectByPrimaryKey" parameterType="${pkPlan.dataType.javaType()}" resultMap="BaseResultMap">
        select
        <include refid="Base_Column_List"/>
        <#if hasBlob>
        ,<include refid="Blob_Column_List"/>
        </#if>
        from <include refid="Query_Table" />
        where ${defaultTableAliasPrefix}${pkPlan.dbColumnName} = ${"#"}{${pkPlan.javaFieldName}}
    </select>

    <select id="selectByCondition" parameterType="com.seezoon.dao.modules.${moduleName}.entity.${className}Condition" resultMap="BaseResultMap">
        select
        <include refid="Base_Column_List"/>
        from <include refid="Query_Table" />
        <#if hasSearch>
        <where>
        <#list columnPlans as columnPlan>
            <#if columnPlan.search>
            <if test="${columnPlan.javaFieldName} != null<#if columnPlan.stringType> and ${columnPlan.javaFieldName} != ''</#if>">
                    <#if columnPlan.queryType.name() == "EQUAL">
            and ${defaultTableAliasPrefix}${columnPlan.dbColumnName} = ${"#"}{${columnPlan.javaFieldName}}
                    <#elseif columnPlan.queryType.name() == "NOT_EQUAL">
            and ${defaultTableAliasPrefix}${columnPlan.dbColumnName} != ${"#"}{${columnPlan.javaFieldName}}
                    <#elseif columnPlan.queryType.name() == "GREATER_EQUAL">
            and <![CDATA[ ${defaultTableAliasPrefix}${columnPlan.dbColumnName} >= ${"#"}{${columnPlan.javaFieldName}} ]]>
                    <#elseif columnPlan.queryType.name() == "GREATER">
            and <![CDATA[ ${defaultTableAliasPrefix}${columnPlan.dbColumnName} > ${"#"}{${columnPlan.javaFieldName}} ]]>
                    <#elseif columnPlan.queryType.name() == "LESS_EQUAL">
            and <![CDATA[ ${defaultTableAliasPrefix}${columnPlan.dbColumnName} <= ${"#"}{${columnPlan.javaFieldName}} ]]>
                    <#elseif columnPlan.queryType.name() == "LESS">
            and <![CDATA[ ${defaultTableAliasPrefix}${columnPlan.dbColumnName} < ${"#"}{${columnPlan.javaFieldName}} ]]>
                    <#elseif columnPlan.queryType.name() == "LIKE">
            and ${defaultTableAliasPrefix}${columnPlan.dbColumnName} like concat("%",${"#"}{${columnPlan.javaFieldName}},"%")
                    <#elseif columnPlan.queryType.name() == "LEFT_LIKE">
            and ${defaultTableAliasPrefix}${columnPlan.dbColumnName} like concat("%",${"#"}{${columnPlan.javaFieldName}})
                    <#elseif columnPlan.queryType.name() == "RIGHT_LIKE">
            and ${defaultTableAliasPrefix}${columnPlan.dbColumnName} like concat(${"#"}{${columnPlan.javaFieldName}},"%")
                    </#if>
            </if>
            </#if>
        </#list>
        </where>
        </#if>
        <choose>
            <when test="sortField != null and sortField != '' and sortOrder != null and sortOrder !=''">
                order by ${defaultTableAliasPrefix}${"$"}{sortField} ${"$"}{sortOrder}
            </when>
            <otherwise>
                <#list columnPlans as columnPlan>
                    <#if columnPlan.dbColumnName == "create_time">
                order by ${defaultTableAliasPrefix}create_time desc
                    <#break>
                    </#if>
                </#list>
            </otherwise>
        </choose>
    </select>

    <delete id="deleteByPrimaryKey">
        delete ${defaultTableAlias} from ${tableName} ${defaultTableAlias}
        where ${defaultTableAliasPrefix}${pkPlan.dbColumnName} in
        <foreach item="item" collection="array" separator="," open="(" close=")">
            ${"#"}{item}
        </foreach>
    </delete>

    <insert id="insert" <#if pkPlan.autoIncrement>keyColumn="${pkPlan.dbColumnName}" keyProperty="${pkPlan.javaFieldName}" useGeneratedKeys="true"</#if>>
        <#assign firstItem = true>
        insert into ${tableName} (<#list columnPlans as columnPlan><#if columnPlan.insert>${firstItem?string("",",")}${columnPlan.dbColumnName}<#assign firstItem = false></#if></#list>)
        values
        <#assign firstItem = true>
        <foreach item="item" collection="array" separator=",">
            (<#list columnPlans as columnPlan><#if columnPlan.insert>${firstItem?string("",",")}${"#"}{item.${columnPlan.dbColumnName}}<#assign firstItem = false></#if></#list>)
        </foreach>
    </insert>

    <update id="updateByPrimaryKeySelective" parameterType="com.seezoon.dao.modules.${moduleName}.entity.${className}">
        update ${tableName} ${defaultTableAlias}
        <set>
        <#list columnPlans as columnPlan>
            <#if columnPlan.update>
            <if test="${columnPlan.javaFieldName} != null">
                ${defaultTableAliasPrefix}${columnPlan.dbColumnName} = ${"#"}{${columnPlan.javaFieldName}}<#sep>,</#sep>
            </if>
            </#if>
        </#list>
        </set>
        where ${defaultTableAliasPrefix}${pkPlan.dbColumnName} = ${"#"}{${pkPlan.javaFieldName}}
    </update>
    <update id="updateByPrimaryKey" parameterType="com.seezoon.dao.modules.${moduleName}.entity.${className}">
        update ${tableName} ${defaultTableAlias} set
        <#assign firstItem = true>
        <#list columnPlans as columnPlan>
            <#if columnPlan.update>
        ${firstItem?string("",",")}${defaultTableAliasPrefix}${columnPlan.dbColumnName} = ${"#"}{${columnPlan.javaFieldName}}
            <#assign firstItem = false>
            </#if>
        </#list>
        where ${defaultTableAliasPrefix}${pkPlan.dbColumnName} = ${"#"}{${pkPlan.javaFieldName}}
    </update>
</mapper>