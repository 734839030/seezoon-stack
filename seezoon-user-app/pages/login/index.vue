<template>
	<view>
		<view style="text-align: center;margin-top: 120rpx;margin-bottom: 35rpx;">
			<image style="width: 80px; height: 80px; border-radius: 40px;" src="../../static/uni.png"></image>
		</view>
		<!-- #ifdef H5 -->
		<account></account>
		<!-- #endif -->
		<!-- #ifdef MP-WEIXIN -->
		<mpwx></mpwx>
		<!-- #endif -->
	</view>
</template>
<script>
	import account from './account.vue';
	import mpwx from './mp-wx.vue';
	export default {
		components:{account,mpwx}
	}
</script>