<template>
  <a-row>
    <a-col :offset="7" :span="10">
      <img alt="Vue logo" src="../../assets/logo.png">
      <a-form ref="form" :label-col="{span:4}" :model="form" :wrapper-col="{span:14}">
        <a-form-item
            :rules="[{required: true, message:'请输入用户名',whitespace:true},{min: 5, max:20,  message: '用户名长度5-20'}]"
            label="用户名" name="username">
          <a-input v-model:value="form.username" :maxlength="20"></a-input>
        </a-form-item>
        <a-form-item :rules="[{required: true, message:'请输入密码',whitespace:true},{min: 6, max:20,  message: '密码6-20之间'}]"
                     label="密码" name="password">
          <a-input-password v-model:value="form.password" :maxlength="20" type="password"></a-input-password>
        </a-form-item>
        <a-form-item :wrapper-col="{offset: 4}">
          <a-checkbox v-model:checked="form.rememberMe">
            记住我
          </a-checkbox>
        </a-form-item>
        <a-form-item :wrapper-col="{offset: 4}">
          <a-button type="primary" @click="onSubmit">登录</a-button>
        </a-form-item>
      </a-form>
    </a-col>
  </a-row>
</template>
<script>
import qs from 'qs'

export default {
  name: 'Login',
  data() {
    return {
      form: {
        username: '',
        password: '',
        rememberMe: false
      }
    }
  },
  methods: {
    onSubmit() {
      this.$refs.form.validate().then(() => {
        console.log('values', this.form);
        this.$http.post('/login', qs.stringify(this.form)).then(() => {
          this.$router.push('/home');
        })
      }).catch((error) => {
        console.log('error', error)
        this.$message.error('请按规范填写登录信息')
      });
    }
  }
}

</script>