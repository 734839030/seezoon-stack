<template>
	<view>
		<view style="margin-left: 50rpx;margin-right: 50rpx;">
			<uni-forms ref="loginForm" :modelValue="loginFormData" :rules="rules">
				<uni-forms-item required label="用户名" name="username">
					<uni-easyinput type="text" placeholder="请输入用户名" v-model="loginFormData.username">
					</uni-easyinput>
				</uni-forms-item>
				<uni-forms-item required label="密码" name="password">
					<uni-easyinput type="password" placeholder="请输入密码" v-model="loginFormData.password">
					</uni-easyinput>
				</uni-forms-item>
				<uni-forms-item label=" " name="rememberMe">
					<uni-data-checkbox multiple v-model="loginFormData.rememberMe" :localdata="[{text:'记住我',value:'true'}]">11</uni-data-checkbox>
				</uni-forms-item>
				<uni-forms-item label=" ">
					<button type="primary" plain="true" size="mini" @click="doLogin">登录</button>
				</uni-forms-item>
			</uni-forms>
		</view>
		<uni-popup ref="popup" type="message" :mask-click="false">
			<uni-popup-message type="error" :message="loginMsg" :duration="2000"></uni-popup-message>
		</uni-popup>
	</view>
</template>

<script>
	import {
		defHttp
	} from '../../static/js/request.js'
	import {setLoginResponseData} from '../../static/js/login.js'
	export default {
		name:'account',
		emits:['redirect'],
		data() {
			return {
				loginMsg: '登录失败',
				rules: {
					username: {
						rules: [{
								required: true,
								errorMessage: '请输入用户名',
							},
							{
								minLength: 5,
								maxLength: 20,
								errorMessage: '用户名长度在 {minLength} 到 {maxLength} 个字符',
							}
						]
					},
					password: {
						rules: [{
							required: true,
							errorMessage: '请输入密码',
						}, {
							minLength: 6,
							maxLength: 20,
							errorMessage: '密码长度在 {minLength} 到 {maxLength} 个字符',
						}]
					}
				},
				loginFormData: {
					username: 'admin',
					password: '123456',
					rememberMe:'true'
				}
			}
		},
		methods: {
			doLogin() {
				this.$refs.loginForm.validate().then(loginData => {
					defHttp.postForm({
						url: '/login',
						data: loginData,
						success: ({
							resp: {
								code,
								msg
							},cookies
						}) => {
							if (code == '0') {
								setLoginResponseData(cookies);
								this.$emit('redirect');
							} else {
								this.loginMsg = msg;
								this.$refs.popup.open();
							}
						}
					})
				}).catch(err => {
					console.log('表单错误信息：', err);
				})
			}
		}
	}
</script>
<style>

</style>
