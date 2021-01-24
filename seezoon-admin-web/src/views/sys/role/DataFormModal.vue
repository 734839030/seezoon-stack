<template>
  <a-modal
      v-model:visible="visible"
      :confirm-loading="confirmLoading" :destroyOnClose="true" :height="600" :maskClosable="false" :title="title"
      :width="750" okText="保存"
      @ok="handleOk(this.dataForm.id === undefined ? '/sys/role/save':'/sys/role/update')">
    <a-form ref="dataForm" :label-col="{ span: 6 }" :model="dataForm" :wrapper-col="{ span: 18 }">
      <a-input v-model:value="dataForm.id" type="hidden"></a-input>
      <a-row>
        <a-col :md="12" :xs="24">
          <a-form-item
              :rules="[
              { required: true, message: '编码不能为空', whitespace: true },
              { min: 1, max: 50, message: '编码长度1-50' },
              { validator: checkCode,trigger: 'blur' }
            ]" label="编码" name="code">
            <a-input
                v-model:value="dataForm.code" :maxlength="50" placeholder="请输入编码">
            </a-input>
          </a-form-item>
        </a-col>
        <a-col :md="12" :xs="24">
          <a-form-item :rules="[
              { required: true, message: '名称不能为空', whitespace: true },
              { min: 1, max: 50, message: '名称长度1-50' },
              { validator: checkName,trigger: 'blur' }
            ]" label="名称" name="name">
            <a-input v-model:value="dataForm.name" :maxlength="50" placeholder="名称">
            </a-input>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :md="12" :xs="24">
          <a-form-item
              :rules="[
              { required: true, message: '数据范围不能为空', whitespace: true },
            ]" label="数据范围" name="dataScope">
            <a-select
                v-model:value="dataForm.dataScope" :allowClear="true" placeholder="请选择数据范围">
              <a-select-option v-for="dict in dataScopeArray" :key="dict.code">{{ dict.name }}</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :md="12" :xs="24">
          <a-form-item
              :rules="[
              {
                required: true,
                type: 'number',
                message: '状态不能为空',
                whitespace: true,
              },
            ]" label="状态" name="status"
          >
            <a-radio-group v-model:value="dataForm.status" name="status">
              <a-radio :value="1">
                <a-badge status="success" text="启用"/>
              </a-radio>
              <a-radio :value="0">
                <a-badge status="warning" text="停用"/>
              </a-radio>
            </a-radio-group>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :span="24">
          <a-form-item :label-col="{ span: 3 }" :wrapper-col="{ span: 21 }" label="备注" name="remarks">
            <a-textarea v-model:value="dataForm.remarks" :auto-size="{ minRows: 3, maxRows: 5 }" :maxlength="255"
                        placeholder="备注"/>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
import {dataFormModalMixin} from "@/mixins/common/data-form-mixin-modal";
import {dataScopeArray} from "@/views/sys/role/data";

export default {
  name: 'DataFormModal',
  mixins: [dataFormModalMixin],
  emits: ['refreshQueryPage'],
  data() {
    return {
      dataScopeArray
    }
  },
  methods: {
    checkCode(rule, value) {
      return this.uniqueFieldSimpleValidation('/sys/role/checkCode', value, {
        id: this.dataForm.id,
        code: value
      }, `编码 ${value} 已存在`)
    },
    checkName(rule, value) {
      return this.uniqueFieldSimpleValidation('/sys/role/checkName', value, {
        id: this.dataForm.id,
        name: value
      }, `名称 ${value} 已存在`)
    },
    // 保存后回调
    handleOkCb() {
      this.$emit('refreshQueryPage');
    }
  },
};
</script>