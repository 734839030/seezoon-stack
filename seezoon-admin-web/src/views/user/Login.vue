<template>
    <a-row>
      <a-col :span="10" :offset="7">
    <img alt="Vue logo" src="../../assets/logo.png">
    <a-form ref="form" :model="form" :rules="rules" :label-col="{span:4}" :wrapper-col="{span:14}"  >
        <a-form-item label="用户名" name="username" :rules="[{required: true, message:'请输入用户名',whitespace:true},{min: 5, max:20,  message: '用户名长度5-20'}]">
            <a-input v-model:value="form.username" :maxlength="20"></a-input>
        </a-form-item>
        <a-form-item label="密码" name="password" :rules="[{required: true, message:'请输入密码',whitespace:true},{min: 6, max:20,  message: '密码6-20之间'}]">
            <a-input-password type="password" v-model:value="form.password" :maxlength="20"></a-input-password>
        </a-form-item>
         <a-form-item :wrapper-col="{offset: 4}">
            <a-checkbox v-model:checked="form.rememberMe" >
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
import {login} from '@/api/user/login'

export default {
    name:'Login',
    data(){
        return {
            rules:{
              username: [{required : false, trigger: 'change' }],// 优先去html 上的rules
            },
            form:{
                username:'',
                password:'',
                rememberMe:false
            }
        }
    },
    methods:{
        onSubmit(){
            this.$refs.form.validate().then(() => {
                console.log('values', this.form);
                login(this.form).then(()=>{
                    this.$router.push('/home');
                    })
                }).catch((error) => {
                  console.log('error',error);
                  this.$message.error('请按规范填写登录信息');
                });
        }
    }
}

</script>