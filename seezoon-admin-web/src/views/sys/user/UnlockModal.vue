<template>
  <a-modal
    v-model:visible="visible"
    :destroyOnClose="true"
    :height="this.height"
    title="解锁"
    :width="600"
    okText="解锁"
    @ok="handleOk"
  >
    <a-form
      ref="dataForm"
      :model="dataForm"
      :label-col="this.labelCol"
      :wrapper-col="this.wrapperCol"
    >
      <a-row>
        <a-col :md="13" :xs="24">
          <a-form-item
            label="解锁方式"
            :rules="[{ required: true, message: '不能为空', whitespace: true }]"
            :label-col="{ span: 8 }"
            :wrapper-col="{ span: 14 }"
          >
            <a-radio-group v-model:value="dataForm.type">
              <a-radio-button value="1">用户名</a-radio-button>
              <a-radio-button value="0">IP</a-radio-button>
            </a-radio-group>
          </a-form-item>
        </a-col>
        <a-col :md="11" :xs="24">
          <a-form-item
            :rules="[
              { required: true, message: '不能为空', whitespace: true },
              { min: 1, max: 50, message: '长度1-50' },
            ]"
            :label="dataForm.type == '0' ? 'IP' : '用户名'"
            name="lockKey"
          >
            <a-input v-model:value="dataForm.lockKey" :maxlength="50" placeholder="" />
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
  import { dataFormModalMixin } from '../../../mixins/common/data-form-mixin-modal';
  import { defHttp } from '../../../utils/http/axios';

  export default {
    name: 'UnlockModal',
    mixins: [dataFormModalMixin],
    methods: {
      open() {
        this.visible = true;
        this.dataForm = { type: '1' };
      },
      handleOk() {
        this.$refs.dataForm.validate().then(() => {
          defHttp.postForm('/sys/user/unlock', this.dataForm).then(() => {
            this.visible = false;
            this.$message.success('解锁成功');
          });
        });
      },
    },
  };
</script>
