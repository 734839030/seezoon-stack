<template>
  <a-modal
    v-model:visible="visible"
    :confirm-loading="confirmLoading"
    :destroyOnClose="true"
    :height="this.height"
    :maskClosable="false"
    :title="title"
    :width="this.width"
    okText="保存"
    @ok="handleOk(this.dataForm.${pkPlan.javaFieldName} === undefined ? '/${moduleName}/${functionName}/save' : '/${moduleName}/${functionName}/update')"
  >
    <a-form
      ref="dataForm"
      :label-col="this.labelCol"
      :model="dataForm"
      :wrapper-col="this.wrapperCol"
    >
      <#list columnPlans as columnPlan>
        <#if columnPlan.showDataForm>
          <#if columnPlan.inputType.name() == "HIDDEN">
      <a-input v-model:value="dataForm.${columnPlan.javaFieldName}" type="hidden" />
          </#if>
        </#if>
      </#list>

      <a-row>
      <#assign itemCount = 0>
      <#list columnPlans as columnPlan>
        <#if columnPlan.showDataForm && columnPlan.inputType.name() != "HIDDEN">
          <a-col :md="12" :xs="24">
            <a-form-item
              label="${columnPlan.fieldName}"
              name="${columnPlan.javaFieldName}"
              :rules="[
             <#if !columnPlan.nullable>
                { required: true,<#if columnPlan.inputType.name() == "INTEGRAL_NUMBER" || columnPlan.inputType.name() == "DECIMAL" || columnPlan.numberType> type: 'number',<#elseif columnPlan.multiple>type: 'array',</#if> message: '${columnPlan.fieldName}不能为空', whitespace: true },
             </#if>
             <#if columnPlan.uniqueFieldCheck>
                { validator: check${columnPlan.javaFieldName?cap_first}, trigger: 'blur' },
             </#if>
                ]"
                >
             <#if columnPlan.inputType.name() == "TEXT">
                <a-input v-model:value="dataForm.${columnPlan.javaFieldName}" <#if columnPlan.maxLength??>:maxlength="${columnPlan.maxLength?c}"</#if> placeholder="" />
             <#elseif columnPlan.inputType.name() == "SELECT">
            <a-select
              v-model:value="dataForm.${columnPlan.javaFieldName}"
              style="width: 140px"
              :allowClear="true"
              :options="${columnPlan.javaFieldName}Dicts"
              placeholder="请选择下拉数据"
            />
             <#elseif columnPlan.inputType.name() == "SELECT_MULTIPLE">
            <a-select
              v-model:value="dataForm.${columnPlan.javaFieldName}Array"
              mode="multiple"
              :allowClear="true"
              :options="${columnPlan.javaFieldName}Dicts"
              placeholder="请选择"
              style="width: 140px"
            />
             <#elseif columnPlan.inputType.name() == "INTEGRAL_NUMBER">
            <a-input-number v-model:value="dataForm.${columnPlan.javaFieldName}" :precision="0" />
             <#elseif columnPlan.inputType.name() == "DECIMAL">
            <a-input-number v-model:value="dataForm.${columnPlan.javaFieldName}" :precision="2" />
             <#elseif columnPlan.inputType.name() == "CHECKBOX">
            <a-checkbox-group v-model:value="dataForm.${columnPlan.javaFieldName}Array" :options="${columnPlan.javaFieldName}Dicts" />
             <#elseif columnPlan.inputType.name() == "RADIO">
            <a-radio-group :options="${columnPlan.javaFieldName}Dicts" v-model:value="dataForm.${columnPlan.javaFieldName}" />
             <#elseif columnPlan.inputType.name() == "DATE">
            <a-date-picker v-model:value="dataForm.${columnPlan.javaFieldName}" valueFormat="YYYY-MM-DD" />
             <#elseif columnPlan.inputType.name() == "DATETIME">
            <a-date-picker v-model:value="dataForm.${columnPlan.javaFieldName}" :showTime="true" valueFormat="YYYY-MM-DD HH:mm:ss" />
             <#elseif columnPlan.inputType.name() == "TEXTAREA">
            <a-textarea
               v-model:value="dataForm.${columnPlan.javaFieldName}"
               :auto-size="{ minRows: 3, maxRows: 5 }"
               <#if columnPlan.maxLength??>:maxlength="${columnPlan.maxLength?c}"</#if>
               placeholder=""
            />
             <#elseif columnPlan.inputType.name() == "RICH_TEXT">
            <tinymce v-model="dataForm.richText" width="100%" />
             <#elseif columnPlan.inputType.name() == "IMAGE">
             <s-uploader v-model:value="dataForm.${columnPlan.javaFieldName}" accept="image/*" listType="picture-card" />
             <#elseif columnPlan.inputType.name() == "FILE">
             <s-uploader v-model:value="dataForm.${columnPlan.javaFieldName}" listType="text" />
             </#if>
            </a-form-item>
          </a-col>
          <#assign itemCount = itemCount + 1>
          <#if itemCount%2 == 0>
      </a-row>
      <a-row>
          </#if>
        </#if>
      </#list>
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
  import { dataFormModalMixin } from '../../../mixins/common/data-form-mixin-modal.js';
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
  <#if hasRichTextWidget>
  import { Tinymce } from '../../../components/Tinymce/index';
  </#if>
  <#if hasFileUploadWidget || hasImageUploadWidget>
  import SUploader from '../../../components/SUploader/index.vue';
  </#if>
  import { defHttp } from '../../../utils/http/axios';

  export default {
    name: 'DataFormModal',
    components: { <#if hasRichTextWidget>Tinymce,</#if> <#if hasFileUploadWidget || hasImageUploadWidget>SUploader</#if> },
    mixins: [dataFormModalMixin],
    emits: ['refreshQuery'],
    setup() {
      <#assign firstItem = true>
      return {<#if hasDictWidget><#list columnPlans as columnPlan><#if columnPlan.dictField>${firstItem?string(""," ,")}${columnPlan.javaFieldName}Dicts, ${columnPlan.javaFieldName}DictsMap<#assign firstItem = false></#if></#list></#if>};
    },
    methods: {
      open(title, ${pkPlan.javaFieldName}) {
        this.visible = true;
        this.title = title;
        if (null != ${pkPlan.javaFieldName}) {
          defHttp.get({ url: '/${moduleName}/${functionName}/query/' + ${pkPlan.javaFieldName} }).then((data) => {
            this.dataForm = data;
          });
        } else {
          this.dataForm = {};
        }
      },
      <#list columnPlans as columnPlan>
        <#if columnPlan.uniqueFieldCheck>
      check${columnPlan.javaFieldName?cap_first}(rule, value) {
          return this.uniqueFieldSimpleValidation(
          '/${moduleName}/${functionName}/check_${columnPlan.underScoreFieldName}',
          value,
          {
            ${pkPlan.javaFieldName}: this.dataForm.${pkPlan.javaFieldName},
            ${columnPlan.javaFieldName}: value,
          },
          `${"$"}{value} 已存在`
        );
      },
        </#if>
      </#list>
      // 保存后回调
      handleOkCb() {
        this.$emit('refreshQuery');
      },
    },
  };
</script>
