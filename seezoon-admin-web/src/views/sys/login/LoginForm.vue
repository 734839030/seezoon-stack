<template>
  <LoginFormTitle v-show="getShow" class="enter-x" />
  <Form v-show="getShow" ref="formRef" :model="formData" :rules="getFormRules" class="p-4 enter-x">
    <FormItem class="enter-x" name="username">
      <Input
        v-model:value="formData.username"
        :placeholder="t('sys.login.userName')"
        :maxlength="20"
        size="large"
      />
    </FormItem>
    <FormItem class="enter-x" name="password">
      <InputPassword
        v-model:value="formData.password"
        :placeholder="t('sys.login.password')"
        size="large"
        :maxlength="20"
        visibilityToggle
      />
    </FormItem>

    <ARow class="enter-x">
      <ACol :span="12">
        <FormItem name="rememberMe">
          <!-- No logic, you need to deal with it yourself -->
          <Checkbox v-model:checked="formData.rememberMe" size="small">
            {{ t('sys.login.rememberMe') }}
          </Checkbox>
        </FormItem>
      </ACol>
      <ACol :span="12" v-if="false">
        <FormItem :style="{ 'text-align': 'right' }">
          <!-- No logic, you need to deal with it yourself -->
          <Button size="small" type="link" @click="setLoginState(LoginStateEnum.RESET_PASSWORD)">
            {{ t('sys.login.forgetPassword') }}
          </Button>
        </FormItem>
      </ACol>
    </ARow>

    <FormItem class="enter-x">
      <Button :loading="loading" block size="large" type="primary" @click="handleLogin">
        {{ t('sys.login.loginButton') }}
      </Button>
      <!--   暂时不需要-->
      <Button v-if="false" block class="mt-4 enter-x" size="large" @click="handleRegister">
        {{ t('sys.login.registerButton') }}
      </Button>
    </FormItem>
    <!--   暂时不需要-->
    <ARow v-if="false" class="enter-x">
      <ACol :span="7">
        <Button block @click="setLoginState(LoginStateEnum.MOBILE)">
          {{ t('sys.login.mobileSignInFormTitle') }}
        </Button>
      </ACol>
      <ACol :offset="1" :span="8">
        <Button block @click="setLoginState(LoginStateEnum.QR_CODE)">
          {{ t('sys.login.qrSignInFormTitle') }}
        </Button>
      </ACol>
      <ACol :offset="1" :span="7">
        <Button block @click="setLoginState(LoginStateEnum.REGISTER)">
          {{ t('sys.login.registerButton') }}
        </Button>
      </ACol>
    </ARow>

    <!--暂时不需要-->
    <Divider v-if="false" class="enter-x hidden">{{ t('sys.login.otherSignIn') }}</Divider>

    <div v-if="false" :class="`${prefixCls}-sign-in-way`" class="flex justify-evenly enter-x">
      <GithubFilled />
      <WechatFilled />
      <AlipayCircleFilled />
      <GoogleCircleFilled />
      <TwitterCircleFilled />
    </div>
  </Form>
</template>
<script lang="ts">
  import { computed, defineComponent, reactive, ref, toRaw, unref } from 'vue';

  import { Button, Checkbox, Col, Divider, Form, Input, Row } from 'ant-design-vue';
  import {
    AlipayCircleFilled,
    GithubFilled,
    GoogleCircleFilled,
    TwitterCircleFilled,
    WechatFilled,
  } from '@ant-design/icons-vue';
  import LoginFormTitle from './LoginFormTitle.vue';

  import { useI18n } from '/@/hooks/web/useI18n';

  import { userStore } from '/@/store/modules/user';
  import { LoginStateEnum, useFormRules, useFormValid, useLoginState } from './useLogin';
  import { useDesign } from '/@/hooks/web/useDesign';

  export default defineComponent({
    name: 'LoginForm',
    components: {
      [Col.name]: Col,
      [Row.name]: Row,
      Checkbox,
      Button,
      Form,
      FormItem: Form.Item,
      Input,
      Divider,
      LoginFormTitle,
      InputPassword: Input.Password,
      GithubFilled,
      WechatFilled,
      AlipayCircleFilled,
      GoogleCircleFilled,
      TwitterCircleFilled,
    },
    setup() {
      const { t } = useI18n();
      // const { notification } = useMessage();
      const { prefixCls } = useDesign('login');

      const { setLoginState, getLoginState } = useLoginState();
      const { getFormRules } = useFormRules();

      const formRef = ref<any>(null);
      const loading = ref(false);

      const formData = reactive({
        username: 'admin',
        password: '123456',
        rememberMe: false,
      });

      const { validForm } = useFormValid(formRef);

      const getShow = computed(() => unref(getLoginState) === LoginStateEnum.LOGIN);

      async function handleLogin() {
        const data = await validForm();
        if (!data) return;
        try {
          loading.value = true;
          /*const userInfo = await */
          await userStore.login(
            toRaw({
              username: data.username,
              password: data.password,
              rememberMe: data.rememberMe,
            })
          );
          /*if (userInfo) {
            notification.success({
              message: t('sys.login.loginSuccessTitle'),
              description: `${t('sys.login.loginSuccessDesc')}: ${userInfo.realName}`,
              duration: 3,
            });
          }*/
        } finally {
          loading.value = false;
        }
      }

      return {
        t,
        prefixCls,
        formRef,
        formData,
        getFormRules,
        handleLogin,
        loading,
        setLoginState,
        LoginStateEnum,
        getShow,
      };
    },
  });
</script>
