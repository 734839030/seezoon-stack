<template>
  <!-- 查询表单 -->
  <a-form
      ref="searchForm"
      :labelCol="this.labelCol"
      :model="searchForm"
      :wrapperCol="this.wrapperCol"
      layout="inline"
  >
<#if hasSearch>
<#list columnPlans as columnPlan>
    <#if columnPlan.search>
    <a-form-item label="${columnPlan.fieldName!}" name="${columnPlan.javaFieldName}">
        <#if columnPlan.inputType.name() == "TEXT">
      <a-input v-model:value="searchForm.${columnPlan.javaFieldName}" <#if columnPlan.stringType>:maxlength="${columnPlan.maxLength}"</#if> placeholder=""/>
        </#if>
        <#if columnPlan.inputType.name() == "SELECT">
      <a-select
         v-model:value="searchForm.${columnPlan.javaFieldName}"
         :allowClear="true"
         :options="${columnPlan.javaFieldName}Dicts"
         placeholder="请选择"
         style="width: 120px"
      />
        </#if>
       <#if columnPlan.inputType.name() == "SELECT_MULTIPLE">
      <a-select
          v-model:value="searchForm.${columnPlan.javaFieldName}"
          mode="tags"
          :allowClear="true"
          :options="${columnPlan.javaFieldName}Dicts"
          placeholder="请选择"
          style="width: 120px"
          :token-separators="[',']"
      />
        </#if>
        <#if columnPlan.inputType.name() == "INTEGRAL_NUMBER">
      <a-input-number v-model:value="searchForm.${columnPlan.javaFieldName}" :precision="0" placeholder=""/>
        </#if>
        <#if columnPlan.inputType.name() == "DECIMAL">
      <a-input-number v-model:value="searchForm.${columnPlan.javaFieldName}" :precision="2" placeholder=""/>
        </#if>
        <#if columnPlan.inputType.name() == "CHECKBOX">
      <a-checkbox-group
          v-model:value="searchForm.${columnPlan.javaFieldName}"
          :options="${columnPlan.javaFieldName}Dicts"
      />
        </#if>
        <#if columnPlan.inputType.name() == "RADIO">
      <a-radio-group v-model:value="searchForm.${columnPlan.javaFieldName}" :options="${columnPlan.javaFieldName}Dicts"/>
        </#if>
        <#if columnPlan.inputType.name() == "DATE">
      <a-date-picker v-model:value="searchForm.${columnPlan.javaFieldName}" valueFormat="YYYY-MM-DD" />
        </#if>
        <#if columnPlan.inputType.name() == "DATETIME">
      <a-date-picker v-model:value="searchForm.${columnPlan.javaFieldName}" :showTime="true" valueFormat="YYYY-MM-DD HH:mm:ss" />
        </#if>
        <#if columnPlan.inputType.name() == "TEXTAREA">
      <a-textarea
        v-model:value="dataForm.${columnPlan.javaFieldName}"
        <#if columnPlan.stringType>
        :maxlength="${columnPlan.maxLength}"
        </#if>
        placeholder=""
      />
        </#if>
    </a-form-item>
   </#if>
</#list>
</#if>
    <a-form-item>
      <a-space>
      <#if !hasSearch>
      <!--
      </#if>
        <a-button v-auth="'${moduleName}:${functionName}:query'" type="primary" @click="handleQuery()">查询</a-button>
      <#if !hasSearch>
      -->
      </#if>
        <a-button type="default" @click="this.$refs.searchForm.resetFields()">重置</a-button>
        <a-button
            v-auth="'${moduleName}:${functionName}:save'"
            type="default"
            @click="this.$refs.dataFormModal.open('添加')"
        >添加
        </a-button>
      </a-space>
    </a-form-item>
  </a-form>
  <a-table
      :columns="columns"
      :data-source="data"
      :loading="loading"
      :pagination="pagination"
      :row-key="(record) => record.${pkPlan.javaFieldName}"
      bordered
      class="mt-4"
      size="small"
      @change="handleTableChange"
  >
    <template #view="{ record, text }">
      <a @click="this.$refs.dataViewModal.open(record.id)">{{ text ? text : '查看' }}</a>
    </template>
    <template #action="{ record }">
      <a v-auth="'${moduleName}:${functionName}:update'" @click="this.$refs.dataFormModal.open('编辑', record.id)"
      >编辑</a
      >
      <a-divider type="vertical"/>
      <a-popconfirm
          placement="left"
          title="确定删除？"
          @confirm="handleDelete('/${moduleName}/${functionName}/delete', record.id)"
      >
        <a v-auth="'${moduleName}:${functionName}:delete'">删除</a>
      </a-popconfirm>
    </template>
  </a-table>
  <data-form-modal ref="dataFormModal" @refreshQuery="handleQuery"/>
  <data-view ref="dataViewModal"/>
</template>
<script>
  import DataFormModal from './DataFormModal.vue';
  import DataView from './DataViewModal.vue';
  import {queryTableMixin} from '../../../mixins/common/query-table-mixin';
  <#if hasDictWidget>
  import {
    <#list columnPlans as columnPlan>
      <#if columnPlan.dictField>
    ${columnPlan.javaFieldName}Dicts,
    ${columnPlan.javaFieldName}DictsMap,
      </#if>
    </#list>
  } from './data.ts';
  </#if>

export default {
  name: 'MainTable',
  components: {DataFormModal, DataView},
  mixins: [queryTableMixin],
  setup() {
    <#assign firstItem = true>
    return {<#if hasDictWidget><#list columnPlans as columnPlan><#if columnPlan.dictField>${firstItem?string(""," ,")}${columnPlan.javaFieldName}Dicts, ${columnPlan.javaFieldName}DictsMap<#assign firstItem = false></#if></#list></#if>};
  },
  data() {
    return {
      url: '/${moduleName}/${functionName}/query',
      columns: [
      <#assign firstItem = true>
      <#list columnPlans as columnPlan>
        <#if columnPlan.list>
        {
          title: '${columnPlan.fieldName}',
          dataIndex: '${columnPlan.javaFieldName}',
          ellipsis: true,
          <#if columnPlan.dictField>
          customRender: function ({text}) {
            return ${columnPlan.javaFieldName}DictsMap.get(text);
          },
          </#if>
          <#if firstItem>
          slots: {customRender: 'view'},
          </#if>
        },
          <#assign firstItem = false>
        </#if>
      </#list>
        {
          title: '操作',
          fixed: 'right',
          width: 120,
          slots: {customRender: 'action'},
        },
      ],
    };
  },
  mounted() {
    this.handleQuery();
  },
};
</script>
