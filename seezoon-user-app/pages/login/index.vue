<template>
	<view>
		<view style="text-align: center;margin-top: 120rpx;margin-bottom: 35rpx;">
			<image style="width: 80px; height: 80px; border-radius: 50%;" src="../../static/uni.png"></image>
		</view>
		<!-- #ifdef H5 -->
		<account @redirect="goOriginalPage"></account>
		<!-- #endif -->
		<!-- #ifdef MP-WEIXIN -->
		<mpwx @redirect="goOriginalPage"></mpwx>
		<!-- #endif -->
	</view>
</template>
<script>
	import account from './account.vue';
	import mpwx from './mp-wx.vue';
	export default {
		components: {
			account,
			mpwx
		},
		data() {
			return {
				goPage: null
			}
		},
		onLoad(options) {
			if (!options.goPage) {
				this.goPage = '/pages/home/index'
			} else {
				this.goPage = decodeURIComponent(options.goPage)
			}
			console.log("goPage:",this.goPage);
		},
		methods: {
			goOriginalPage() {
				uni.switchTab({
					url: this.goPage,
					fail: (res) => {
						uni.redirectTo({
							url: this.goPage
						})
					}
				})
			}
		}
	}
</script>
