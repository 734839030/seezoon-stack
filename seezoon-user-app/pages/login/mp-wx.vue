<template>
	<view style="margin: 40rpx;">
		<button v-if="needRegist" type="primary" plain="true" open-type="getPhoneNumber"
			@getphonenumber="getPhoneNumber">一键登录</button>
		<button v-if="relogin" type="primary" plain="true" @click="login">登录</button>
	</view>
</template>

<script>
	import {
		defHttp
	} from '../../static/js/request.js'
	import {
		setLoginResponseData
	} from '../../static/js/login.js'

	export default {
		name: 'mpwx',
		emits:['redirect'],
		data() {
			return {
				needRegist: false,
				code:null,
				relogin:false
			}
		},
		mounted() {
			console.log("mounted")
			this.login();
		},
		methods: {
			login() {
				wx.login({
					success: (res) => {
						if (res.code) {
							console.log(res.code)
							// 先自动登录,未注册时候再次获取code
							defHttp.postForm({
								url: '/login?type=MP_WEIXIN',
								data: {
									username: res.code,
									password:'none'
								},
								success: ({
									resp: {
										code,
										msg
									},
									cookies
								}) => {
									if (code == '0') {
										setLoginResponseData(cookies);
										console.log("setLoginResponseData")
										this.$emit('redirect');
									} else if (code == '80001') { // 微信未注册，后台约定错误码
										this.relogin=false;
										wx.login({
											success: (res) => {
												this.code = res.code
												this.needRegist = true
											}});
									} else {
										this.relogin = true
										uni.showToast({
											icon: 'none',
											title: msg
										})
									}
								}
							});
						} else {
							uni.showToast({
								icon: 'none',
								title: res.errMsg
							})
						}
					}
				})
			},
			getPhoneNumber(e) {
				console.log(e.detail.errMsg)
				if (e.detail.iv && e.detail.encryptedData) {
					defHttp.postForm({
						url:'/public/mp_regist',
						data:{
							code:this.code,
							encryptedData:e.detail.encryptedData,
							iv:e.detail.iv
						},
						success:({resp:{code,msg}})=>{
							if (code == '0') {
								this.login();
							} else {
								uni.showToast({
									icon: 'none',
									title: msg
								})
							}
						}
					})
				}
			}
		}
	}
</script>

<style scoped>
</style>
