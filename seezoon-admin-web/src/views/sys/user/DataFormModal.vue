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
    @ok="handleOk(this.dataForm.id === undefined ? '/sys/user/save' : '/sys/user/update')"
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
          <a-form-item label="头像">
            <s-uploader v-model:value="dataForm.photo" accept="image/*" listType="picture-card" />
          </a-form-item>
        </a-col>
        <a-col :md="12" :xs="24">
          <a-form-item
            :rules="[
              { required: true, message: '登录名不能为空', whitespace: true },
              { min: 1, max: 50, message: '登录名长度1-50' },
              { validator: checkUsername, trigger: 'blur' },
            ]"
            label="登录名"
            name="username"
          >
            <a-input
              v-model:value="dataForm.username"
              :maxlength="50"
              placeholder="请输入登录名字符或者数字"
            />
          </a-form-item>
          <a-form-item
            :rules="[
              { required: true, message: '姓名不能为空', whitespace: true },
              { min: 1, max: 50, message: '姓名长度1-50' },
            ]"
            label="姓名"
            name="name"
          >
            <a-input
              v-model:value="dataForm.name"
              :maxlength="50"
              autocomplete="off"
              placeholder="请输入姓名"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :md="12" :xs="24">
          <a-form-item
            :rules="[
              { required: true, message: '手机号不能为空', whitespace: true },
              { len: 11, message: '手机号长度为11位' },
              { validator: checkMobile, trigger: 'blur' },
            ]"
            label="手机"
            name="mobile"
          >
            <a-input
              v-model:value="dataForm.mobile"
              :maxlength="11"
              placeholder="请输入11位手机号"
            />
          </a-form-item>
        </a-col>
        <a-col :md="12" :xs="24">
          <a-form-item :rules="[]" label="部门" name="deptId">
            <a-tree-select
              v-model:value="dataForm.deptId"
              :allowClear="true"
              :load-data="loadDeptData"
              :tree-data="deptTreeData"
              placeholder="请选择部门"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :md="12" :xs="24">
          <a-form-item
            :rules="[{ type: 'email', message: '请输入合法邮箱' }]"
            label="邮箱"
            name="email"
          >
            <a-input v-model:value="dataForm.email" :maxlength="50" placeholder="请输入合法邮箱" />
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
            ]"
            label="状态"
            name="status"
          >
            <a-radio-group v-model:value="dataForm.status" name="status">
              <a-radio :value="1">
                <a-badge status="success" text="正常" />
              </a-radio>
              <a-radio :value="0">
                <a-badge status="warning" text="禁用" />
              </a-radio>
            </a-radio-group>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :md="12" :xs="24">
          <a-form-item :rules="passwordRules" label="密码" name="password">
            <a-input-password
              v-model:value="dataForm.password"
              :maxlength="50"
              allow-clear
              autocomplete="new-password"
              placeholder="请输入密码"
            />
          </a-form-item>
        </a-col>
        <a-col :md="12" :xs="24">
          <a-form-item :rules="passwordRules" label="确认密码" name="confirmPassword">
            <a-input-password
              v-model:value="dataForm.confirmPassword"
              :maxlength="50"
              allow-clear
              autocomplete="new-password"
              placeholder="请确认密码"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :md="12" :xs="24">
          <a-form-item label="角色">
            <a-select
              v-model:value="dataForm.roleIds"
              :allowClear="true"
              :options="roles"
              mode="multiple"
              placeholder="请选择合适的角色"
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
  import { onMounted, ref } from 'vue';
  import { getRoles } from '../../../api/sys';
  import SUploader from '../../../components/SUploader/index.vue';
  import { dataFormModalMixin } from '../../../mixins/common/data-form-mixin-modal';
  import { deptTreeSelectMixin } from '../../../mixins/sys/dept-tree-select-mixin';

  export default {
    name: 'DataFormModal',
    components: { SUploader },
    mixins: [dataFormModalMixin, deptTreeSelectMixin],
    emits: ['refreshQuery'],
    setup() {
      const roles = ref([]);
      onMounted(async () => {
        roles.value = await getRoles();
      });
      return {
        roles,
      };
    },
    computed: {
      passwordRules() {
        let passwordRules = [];
        if (!this.dataForm.id) {
          passwordRules.push({ required: true, message: '密码不能为空', whitespace: true });
        }
        passwordRules.push({ min: 6, max: 50, message: '密码长度6-50' });
        passwordRules.push({ validator: this.checkPassword, trigger: 'blur' });
        return passwordRules;
      },
    },
    methods: {
      checkUsername(rule, value) {
        return this.uniqueFieldSimpleValidation(
          '/sys/user/check_user_name',
          value,
          {
            id: this.dataForm.id,
            username: value,
          },
          `登录名 ${value} 已存在`
        );
      },
      checkMobile(rule, value) {
        return this.uniqueFieldSimpleValidation(
          '/sys/user/check_mobile',
          value,
          {
            id: this.dataForm.id,
            mobile: value,
          },
          `手机号 ${value} 已存在`
        );
      },
      checkPassword(rule, value) {
        return new Promise((resolve, reject) => {
          if (!(value && value.trim())) {
            resolve();
            return;
          }
          if (this.dataForm.password === this.dataForm.confirmPassword) {
            this.$refs.dataForm.clearValidate(
              rule.field === 'password' ? 'confirmPassword' : 'password'
            );
            resolve();
          } else {
            reject('密钥和确认密码不一致');
          }
        });
      },
      // 保存后回调
      handleOkCb() {
        this.$emit('refreshQuery');
      },
    },
  };
</script>
