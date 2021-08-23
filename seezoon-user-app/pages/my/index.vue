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
			<image class="user-image" :src="userInfo.photoUrl? userInfo.photoUrl : '../../static/user.png'"></image>
			<text>{{userInfo.name?userInfo.name:userInfo.mobile}}</text>
			<!-- #endif -->
		</view>
		<view class="function-list">
			<uni-list>
				    <uni-list-item  title="列表文字" :show-badge="true" badge-text="12"></uni-list-item>
			</uni-list>
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
			this.getUserInfo();
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
		border-radius: 50%;
		overflow: hidden;
	}
</style>
