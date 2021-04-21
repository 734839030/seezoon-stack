<template>
  <a-modal
    v-model:visible="visible"
    :destroyOnClose="true"
    :footer="null"
    :height="800"
    :maskClosable="false"
    :title="title"
    :width="750"
  >
    <a-tabs v-model:activeKey="activeKey" tabPosition="left">
      <a-tab-pane key="info">
        <template #tab>
          <span>
            <icon icon="carbon:user-identification" />
            基本信息
          </span>
        </template>
        <a-form
          ref="userInfoFormRef"
          :label-col="{ span: 7 }"
          :model="userInfoForm"
          :wrapper-col="{ span: 17 }"
        >
          <a-row>
            <a-col :md="12" :xs="24">
              <a-form-item label="所在部门"
                >{{ userInfoForm.deptName || '未分配部门' }}
              </a-form-item>
              <a-form-item label="登录账号">{{ userInfoForm.username }}</a-form-item>
              <a-form-item label="手机">{{ userInfoForm.mobile }}</a-form-item>
            </a-col>
            <a-col :md="12" :xs="24">
              <a-form-item label="头像">
                <s-uploader
                  v-model:value="userInfoForm.photo"
                  accept="image/*"
                  listType="picture-card"
                />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row>
            <a-col :md="12" :xs="24">
              <a-form-item
                :rules="[
                  { required: true, message: '姓名不能为空', whitespace: true },
                  { min: 1, max: 50, message: '姓名长度1-50' },
                ]"
                label="姓名"
                name="name"
              >
                <a-input
                  v-model:value="userInfoForm.name"
                  :maxlength="50"
                  placeholder="请输入姓名"
                />
              </a-form-item>
              <a-form-item
                :rules="[{ type: 'email', message: '请输入合法邮箱' }]"
                label="邮箱"
                name="email"
              >
                <a-input
                  v-model:value="userInfoForm.email"
                  :maxlength="50"
                  placeholder="请输入合法邮箱"
                />
              </a-form-item>
              <a-form-item :wrapper-col="{ span: 21, offset: 3 }">
                <a-button type="primary" @click="handleSave">保存</a-button>
              </a-form-item>
            </a-col>
          </a-row>
        </a-form>
      </a-tab-pane>
      <a-tab-pane key="password">
        <template #tab>
          <span>
            <icon icon="vaadin:password" />
            修改密码
          </span>
        </template>
        <a-alert banner closable message="密码修改成功后将重新回到登录页" show-icon />
        <div class="mt-4">
          <a-form
            ref="userPasswordFormRef"
            :label-col="{ span: 7 }"
            :model="userPasswordForm"
            :wrapper-col="{ span: 17 }"
          >
            <a-row>
              <a-col :md="12" :xs="24">
                <a-form-item :rules="passwordRules" label="原密码" name="oldPassword">
                  <a-input-password
                    v-model:value="userPasswordForm.oldPassword"
                    :maxlength="50"
                    allow-clear
                    autocomplete="new-password"
                    placeholder="请输入密码"
                  />
                </a-form-item>
                <a-form-item :rules="passwordRules" label="新密码" name="newPassword">
                  <a-input-password
                    v-model:value="userPasswordForm.newPassword"
                    :maxlength="50"
                    allow-clear
                    autocomplete="new-password"
                    placeholder="请确认新密码"
                  />
                </a-form-item>
                <a-form-item :rules="passwordRules" label="确认密码" name="confirmPassword">
                  <a-input-password
                    v-model:value="userPasswordForm.confirmPassword"
                    :maxlength="50"
                    allow-clear
                    autocomplete="new-password"
                    placeholder="请确认密码"
                  />
                </a-form-item>
                <a-form-item :wrapper-col="{ span: 20, offset: 4 }">
                  <a-button type="primary" @click="handleModifyPassword">修改</a-button>
                </a-form-item>
              </a-col>
            </a-row>
          </a-form>
        </div>
      </a-tab-pane>
    </a-tabs>
  </a-modal>
</template>
<script>
  import { reactive, ref } from 'vue';
  import { getUserInfo } from '../../../../../api/sys/user';
  import { defHttp } from '../../../../../utils/http/axios';
  import { useUserStore } from '../../../../../store/modules/user';
  import SUploader from '../../../../../components/SUploader/index.vue';
  import { Icon } from '../../../../../components/Icon';

  export default {
    name: 'UserCenterModal',
    components: { SUploader, Icon },
    props: { title: String },
    setup() {
      const activeKey = ref('');

      const visible = ref(false);
      const userInfoForm = reactive({});
      const userPasswordForm = ref({});
      const init = async () => {
        const { userId, username, deptName, name, mobile, email, photo } = await getUserInfo();
        userInfoForm.userId = userId;
        userInfoForm.username = username;
        userInfoForm.deptName = deptName;
        userInfoForm.name = name;
        userInfoForm.mobile = mobile;
        userInfoForm.email = email;
        userInfoForm.photo = photo;
        userPasswordForm.value = {};
        activeKey.value = 'info';
      };
      init();
      const show = function () {
        visible.value = true;
        init();
      };
      const userStore = useUserStore();
      return {
        userPasswordForm,
        visible,
        show,
        activeKey,
        userInfoForm,
        userStore,
      };
    },
    computed: {
      passwordRules() {
        let passwordRules = [];
        passwordRules.push({ required: true, message: '密码不能为空', whitespace: true });
        passwordRules.push({ min: 6, max: 50, message: '密码长度6-50' });
        passwordRules.push({ validator: this.checkPassword });
        return passwordRules;
      },
    },
    methods: {
      handleModifyPassword() {
        this.$refs.userPasswordFormRef.validate().then(() => {
          defHttp.postForm('/sys/user/update_password', this.userPasswordForm).then(() => {
            this.$refs.userPasswordFormRef.resetFields();
            this.$message.success('修改成功,请重新登录');
            this.userStore.logout(true);
          });
        });
      },
      checkPassword(rule, value) {
        return new Promise((resolve, reject) => {
          if (!(value && value.trim())) {
            resolve();
            return;
          }
          if (this.userPasswordForm.newPassword === this.userPasswordForm.confirmPassword) {
            this.$refs.userPasswordFormRef.clearValidate(
              rule.field === 'newPassword' ? 'confirmPassword' : 'newPassword'
            );
            resolve();
          } else {
            reject('新密钥和确认密码不一致');
          }
        });
      },
      handleSave() {
        this.$refs.userInfoFormRef
          .validate()
          .then(() => {
            defHttp
              .post({
                url: '/sys/user/save_user_info',
                params: {
                  name: this.userInfoForm.name,
                  email: this.userInfoForm.email,
                  photo: this.userInfoForm.photo,
                },
              })
              .then(() => {
                getUserInfo().then((userInfo) => {
                  this.userStore.setUserInfo(userInfo);
                });
                this.$message.success('保存成功');
              });
          })
          .catch((error) => {
            console.log('error', error);
          });
      },
    },
  };
</script>
