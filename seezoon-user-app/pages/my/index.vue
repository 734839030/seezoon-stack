<template>
	<view class="center">
		<view class="user-image-container">
			<!-- #ifdef MP-WEIXIN -->
			<view class="user-image">
				<open-data type="userAvatarUrl"></open-data>
			</view>
			<view>
		    <open-data type="userNickName"></open-data>
			</view>
			<!-- #endif -->
			<!-- #ifdef H5 -->
			<view class="user-image">
				<image src="../../static/user.png"></image>
			</view>
			<!-- #endif -->
		</view>
	</view>
</template>

<script>
	import {defHttp} from '../../static/js/request.js';
	export default {
		data() { 
			return {
				userInfo:{}
			}
		},
		mounted() {
			this.getUserInfo();
		},
		onPullDownRefresh(){
			console.log('onPullDownRefresh');
		},
		methods: {
		  getUserInfo() {
			  defHttp.get({
			  	url:'/sys/user/info',
			  	success: ({resp : {code,data}}) => {
			  		if (code == '0') {
			  			this.userInfo = data;
			  		} 
			  	}
			  });
		  }
		}
	}
</script>

<style scoped>
	.user-image-container {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
		height: 240rpx;
	}
	.user-image {
		height: 150rpx;
		width: 150rpx;
		overflow: hidden;
	}
</style>
