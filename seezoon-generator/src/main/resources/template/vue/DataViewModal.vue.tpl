<template>
  <a-modal
    v-model:visible="visible"
    :destroyOnClose="true"
    :height="600"
    title="查看"
    :width="750"
    :footer="null"
  >
    <a-descriptions bordered size="small">
      <#list columnPlans as columnPlan>
        <#if showView>
        <a-descriptions-item label="${columnPlan.fieldName}">
          <#if columnPlan.dictField>
           <#if columnPlan.inputType.name() == "SELECT_MULTIPLE">
           <a-select
             v-model:value="data.${columnPlan.javaFieldName}"
             mode="tags"
             :options="${columnPlan.javaFieldName}Dicts"
             style="width: 120px"
             :token-separators="[',']"
             :disabled="true"
           />
           <#else>
          {{ ${columnPlan.javaFieldName}DictsMap.get(data.${columnPlan.javaFieldName}) }}
           </#if>
          <#else if columnPlan.inputType.name() == "IMAGE">
          <s-uploader v-model:value="data.${columnPlan.javaFieldName}" :disabled="true" listType="picture-card" />
          <#else if columnPlan.inputType.name() == "FILE">
          <s-uploader v-model:value="data.${columnPlan.javaFieldName}" :disabled="true" />
          <#else if columnPlan.inputType.name() == "RICH_TEXT">
          <p v-html="data.${columnPlan.javaFieldName}"></p>
          <#else>
          {{ data.${columnPlan.javaFieldName} }}
          </#if>
        </a-descriptions-item>
        </#if>
      </#list>
    </a-descriptions>
  </a-modal>
</template>

<script>
  import { ref } from 'vue';
  import { defHttp } from '../../../utils/http/axios';
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
  <#if hasFileUploadWidget || hasImageUploadWidget>
  import SUploader from '../../../components/SUploader/index.vue';
  </#if>
  export default {
    name: 'DataViewModal',
    <#if hasFileUploadWidget || hasImageUploadWidget>
    components: { SUploader },
    </#if>
    setup() {
      const visible = ref(false);
      const data = ref({});
      const open = function (id) {
        visible.value = true;
        defHttp.get({ url: '/sys/demo/query/' + id }).then((_data) => {
          data.value = _data;
        });
      };
      return {
        visible,
        open,
        data,
        <#if hasDictWidget>
          <#list columnPlans as columnPlan>
            <#if columnPlan.dictField>
        ${columnPlan.javaFieldName}Dicts,
        ${columnPlan.javaFieldName}DictsMap,
            </#if>
          </#list>
        </#if>
      };
    },
  };
</script>
