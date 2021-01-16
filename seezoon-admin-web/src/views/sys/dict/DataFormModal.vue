<template>
  <a-modal v-model:visible="visible"
           :confirm-loading="confirmLoading" :destroyOnClose="true" :height="600" :maskClosable="false"
           :title="title" :width="750" okText="保存"
           @ok="handleOk(this.dataForm.id === undefined ? '/sys/dict/save':'/sys/dict/update')">
    <a-form ref="dataForm" :label-col="{ span: 6 }" :model="dataForm" :wrapper-col="{ span: 18 }">
      <a-input v-model:value="dataForm.id" type="hidden"></a-input>
      <a-row>
        <a-col :md="12" :xs="24">
          <a-form-item :rules="[
              { required: true, message: '类型不能为空', whitespace: true },
              { min: 1, max: 50, message: '类型长度1-50' },
              { validator: checkTypeAndCode ,trigger: 'blur'}
            ]" label="类型" name="type">
            <a-input v-model:value="dataForm.type" :maxlength="50" placeholder="请输入类型"></a-input>
          </a-form-item>
        </a-col>
        <a-col :md="12" :xs="24">
          <a-form-item :rules="[
              { required: true, message: '编码不能为空', whitespace: true },
              { min: 1, max: 50, message: '编码长度1-50' },
              { validator: checkTypeAndCode ,trigger: 'blur'}
            ]" label="编码" name="code">
            <a-input v-model:value="dataForm.code" :maxlength="50" placeholder="请输入编码"></a-input>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :md="12" :xs="24">
          <a-form-item :rules="[
              { required: true, message: '名称不能为空', whitespace: true },
              { min: 1, max: 50, message: '名称长度1-50' },
            ]" label="名称" name="name">
            <a-input v-model:value="dataForm.name" :maxlength="50" placeholder="请输入名称"></a-input>
          </a-form-item>
        </a-col>
        <a-col :md="12" :xs="24">
          <a-form-item :rules="[
              {required: true, type: 'number',message: '状态不能为空',whitespace: true}
            ]" label="状态" name="status">
            <a-radio-group v-model:value="dataForm.status" name="status">
              <a-radio :value="1">
                <a-badge status="success" text="有效"/>
              </a-radio>
              <a-radio :value="0">
                <a-badge status="warning" text="无效"/>
              </a-radio>
            </a-radio-group>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :md="12" :xs="24">
          <a-form-item :rules="[
              { required: true, type:'number', message: '请输入数字', whitespace: true },
            ]" help="顺序越大显示越前" label="顺序" name="sort">
            <a-input-number v-model:value="dataForm.sort" :precision="0" placeholder="请输入顺序"></a-input-number>
          </a-form-item>
        </a-col>
        <a-col :md="12" :xs="24">
          <a-form-item label="备注" name="remarks">
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
import qs from 'qs'

export default {
  name: 'DataFormModal',
  mixins: [dataFormModalMixin],
  emits: ['refreshQueryPage'],
  methods: {
    checkTypeAndCode(rule, value) {
      // 参数验证
      return new Promise((resolve, reject) => {
        if (!(this.dataForm.type && this.dataForm.type.trim())) {
          resolve();
          return;
        }
        if (!(this.dataForm.code && this.dataForm.code.trim())) {
          resolve();
          return;
        }
        this.$http
            .post(
                '/sys/dict/checkTypeAndCode',
                qs.stringify({id: this.dataForm.id, type: this.dataForm.type, code: this.dataForm.code})
            ).then(({data}) => {
          this.$refs.dataForm.clearValidate(rule.field === 'type' ? 'code' : 'type');
          data ? resolve() : reject(`当前类型${this.dataForm.type},编码${this.dataForm.code} 已存在`);
        });
      });
    },
    // 保存后回调
    handleOkCb() {
      this.$emit('refreshQueryPage');
    }
  },
};
</script>