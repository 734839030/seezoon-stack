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
    @ok="handleOk(this.dataForm.id === undefined ? '/sys/dept/save' : '/sys/dept/update')"
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
          <a-form-item :rules="[]" label="父部门" name="parentId">
            <a-tree-select
              v-model:value="dataForm.parentId"
              :allowClear="true"
              :load-data="loadDeptData"
              :tree-data="deptTreeData"
              placeholder="请选择上级部门"
            />
          </a-form-item>
        </a-col>
        <a-col :md="12" :xs="24">
          <a-form-item
            :rules="[
              { required: true, message: '名称不能为空', whitespace: true },
              { min: 1, max: 50, message: '名称长度1-50' },
              { validator: checkName, trigger: 'blur' },
            ]"
            label="名称"
            name="name"
          >
            <a-input v-model:value="dataForm.name" :maxlength="50" placeholder="名称" />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :md="12" :xs="24">
          <a-form-item label="联系人" name="contactUser">
            <a-input v-model:value="dataForm.contactUser" :maxlength="50" placeholder="联系人" />
          </a-form-item>
        </a-col>
        <a-col :md="12" :xs="24">
          <a-form-item label="联系电话" name="telephone">
            <a-input v-model:value="dataForm.telephone" :maxlength="50" placeholder="联系电话" />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :md="12" :xs="24">
          <a-form-item
            :rules="[{ required: true, type: 'number', message: '请输入数字', whitespace: true }]"
            help="顺序越小显示越前"
            label="顺序"
            name="sort"
          >
            <a-input-number
              v-model:value="dataForm.sort"
              :max="10000"
              :precision="0"
              :step="10"
              placeholder="请输入顺序"
            />
          </a-form-item>
        </a-col>
        <a-col :md="12" :xs="24">
          <a-form-item label="备注" name="remarks">
            <a-textarea
              v-model:value="dataForm.remarks"
              :auto-size="{ minRows: 3, maxRows: 5 }"
              :maxlength="255"
              placeholder="备注"
            />
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
  import { dataFormModalMixin } from '../../../mixins/common/data-form-mixin-modal.js';
  import { deptTreeSelectMixin } from '../../../mixins/sys/dept-tree-select-mixin.js';

  export default {
    name: 'DataFormModal',
    mixins: [dataFormModalMixin, deptTreeSelectMixin],
    emits: ['refreshQuery', 'refreshDeptTree'],
    methods: {
      checkName(rule, value) {
        return this.uniqueFieldSimpleValidation(
          '/sys/dept/check_name',
          value,
          {
            id: this.dataForm.id,
            name: value,
            parentId: this.dataForm.parentId,
          },
          `该层级部门 ${value} 已存在`
        );
      },
      // 保存后回调
      handleOkCb() {
        this.$emit('refreshQuery');
        this.$emit('refreshDeptTree');
      },
    },
  };
</script>
