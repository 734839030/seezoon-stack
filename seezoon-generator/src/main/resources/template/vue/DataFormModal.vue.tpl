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
    @ok="handleOk(this.dataForm.id === undefined ? '/sys/demo/save' : '/sys/demo/update')"
  >
    <a-form
      ref="dataForm"
      :label-col="this.labelCol"
      :model="dataForm"
      :wrapper-col="this.wrapperCol"
    >
      <a-input v-model:value="dataForm.id" type="hidden" />
      <a-row>
        <a-col :md="12" :xs="24">
          <a-form-item
            :rules="[
              { required: true, message: '文本不能为空', whitespace: true },
              { validator: checkInputText, trigger: 'blur' },
            ]"
            label="文本"
            name="inputText"
          >
            <a-input v-model:value="dataForm.inputText" :maxlength="50" placeholder="文本" />
          </a-form-item>
        </a-col>
        <a-col :md="12" :xs="24">
          <a-form-item
            :rules="[{ required: true, message: '下拉不能为空', whitespace: true }]"
            label="下拉"
            name="inputSelect"
          >
            <a-select
              v-model:value="dataForm.inputSelect"
              style="width: 120px"
              :allowClear="true"
              :options="inputSelectDicts"
              placeholder="请选择下拉数据"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :md="12" :xs="24">
          <a-form-item
            :rules="[{ required: true, message: '单选不能为空', whitespace: true }]"
            label="单选"
            name="inputRadio"
          >
            <a-radio-group :options="inputRadioDicts" v-model:value="dataForm.inputRadio" />
          </a-form-item>
        </a-col>
        <a-col :md="12" :xs="24">
          <a-form-item
            :rules="[{ required: true, type: 'array', message: '多选不能为空', whitespace: true }]"
            label="多选"
            name="inputCheckboxTODO"
          >
            <a-checkbox-group
              v-model:value="dataForm.inputCheckboxTODO"
              :options="inputCheckboxDicts"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :md="12" :xs="24">
          <a-form-item label="文本域" name="inputTextarea" :rules="[]">
            <a-textarea
              v-model:value="dataForm.inputTextarea"
              :auto-size="{ minRows: 3, maxRows: 5 }"
              :maxlength="255"
              placeholder=""
            />
          </a-form-item>
        </a-col>
        <a-col :md="12" :xs="24">
          <a-form-item
            label="日期"
            name="inputDate"
            :rules="[{ required: true, message: '日期不能为空', whitespace: true }]"
          >
            <a-date-picker v-model:value="dataForm.inputDate" valueFormat="YYYY-MM-DD" />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :md="12" :xs="24">
          <a-form-item
            label="整数"
            name="inputZhengshu"
            :rules="[{ required: true, type: 'number', message: '整数不能为空', whitespace: true }]"
          >
            <a-input-number v-model:value="dataForm.inputZhengshu" :precision="0" />
          </a-form-item>
        </a-col>
        <a-col :md="12" :xs="24">
          <a-form-item
            label="小数"
            name="inputXiaoshu"
            :rules="[
              { required: true, type: 'number', message: '小数数不能为空', whitespace: true },
            ]"
          >
            <a-input-number v-model:value="dataForm.inputXiaoshu" :precision="0" />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :md="12" :xs="24">
          <a-form-item
            label="富文本"
            name="richText"
            :rules="[{ required: true, message: '富文本不能为空', whitespace: true }]"
          >
            <tinymce v-model="dataForm.richText" width="100%" />
          </a-form-item>
        </a-col>
        <a-col :md="12" :xs="24">
          <a-form-item
            label="图片"
            name="image"
            :autoLink="false"
            :rules="[{ required: true, message: '图片不能为空', whitespace: true }]"
          >
            <s-uploader v-model:value="dataForm.image" accept="image/*" listType="picture-card" />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :md="12" :xs="24">
          <a-form-item
            label="文件"
            name="file"
            :autoLink="false"
            :rules="[{ required: true, message: '文件不能为空', whitespace: true }]"
          >
            <s-uploader v-model:value="dataForm.file" listType="text" />
          </a-form-item>
        </a-col>
        <a-col :md="12" :xs="24" />
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
  import { dataFormModalMixin } from '../../../mixins/common/data-form-mixin-modal.js';
  import { inputSelectDicts, inputRadioDicts, inputCheckboxDicts } from './data';
  import { Tinymce } from '../../../components/Tinymce/index';
  import SUploader from '../../../components/SUploader/index.vue';
  import { defHttp } from '../../../utils/http/axios';

  export default {
    name: 'DataFormModal',
    components: { Tinymce, SUploader },
    mixins: [dataFormModalMixin],
    emits: ['refreshQuery'],
    setup() {
      return { inputSelectDicts, inputRadioDicts, inputCheckboxDicts };
    },
    methods: {
      open(title, id) {
        this.visible = true;
        this.title = title;
        if (null != id) {
          defHttp.get({ url: '/sys/demo/query/' + id }).then((data) => {
            this.dataForm = data;
          });
        } else {
          this.dataForm = { sort: 1000 };
        }
      },
      checkInputText(rule, value) {
        return this.uniqueFieldSimpleValidation(
          '/sys/demo/check_input_text',
          value,
          {
            id: this.dataForm.id,
            inputText: value,
          },
          `${value} 已存在`
        );
      },
      // 保存后回调
      handleOkCb() {
        this.$emit('refreshQuery');
      },
    },
  };
</script>
