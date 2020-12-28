<template>
  <a-modal
      v-model:visible="visible"
      :confirm-loading="confirmLoading" :destroyOnClose="true" :height="600" :maskClosable="false" :title="title"
      :width="750" okText="保存"
      @ok="handleOk(this.dataForm.id === undefined ? '/sys/param/save':'/sys/param/update')">
    <a-form ref="dataForm" :label-col="{ span: 6 }" :model="dataForm" :wrapper-col="{ span: 18 }">
      <a-input v-model:value="dataForm.id" type="hidden"></a-input>
      <a-row>
        <a-col :span="12">
          <a-form-item
              :rules="[
              { required: true, message: '参数名不能为空', whitespace: true },
              { min: 1, max: 50, message: '参数名长度1-50' },
            ]" label="参数名" name="name">
            <a-input
                v-model:value="dataForm.name" :maxlength="50" placeholder="参数名">
            </a-input>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item :rules="[
              { required: true, message: '唯一键不能为空', whitespace: true },
              { min: 1, max: 50, message: '唯一键长度1-50' },
              { validator: checkParamKey }
            ]" label="唯一键" name="paramKey">
            <a-input v-model:value="dataForm.paramKey" :maxlength="50" placeholder="唯一键">
            </a-input>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :span="12">
          <a-form-item
              :rules="[
              { required: true, message: '参数值不能为空', whitespace: true },
              { min: 1, max: 100, message: '参数值长度1-100' },
            ]" label="参数值" name="paramValue"
          >
            <a-input
                v-model:value="dataForm.paramValue" :maxlength="100" placeholder="参数值">
            </a-input>
          </a-form-item>
        </a-col>
        <a-col :span="12">
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
import {dataFormModalMixin} from "@/views/common/mixins/data-form-mixin-modal";
import qs from 'qs'

export default {
  name: 'DataFormModal',
  mixins: [dataFormModalMixin],
  emits: ['refreshQueryPage'],
  methods: {
    checkParamKey(rule, value) {
      // 参数验证
      return new Promise((resolve, reject) => {
        if (!(value && value.trim())) {
          resolve();
          return;
        }
        this.$http
            .post(
                '/sys/param/checkParamKey',
                qs.stringify({id: this.dataForm.id, paramKey: value})
            ).then(({data}) => {
          data ? resolve() : reject(`唯一键 ${value} 已存在`);
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