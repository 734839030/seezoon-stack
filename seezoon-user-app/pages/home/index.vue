<template>
	<view class="container" v-if="userInfo">
		姓名：{{userInfo.name}}
	</view>
</template>
<script>
	import {defHttp} from '../../static/js/request.js'
	export default {
		setup() {
		},
		data() {
			return {
				userInfo:null
			}
		},
		mounted() {
			defHttp.get({
				url:'/sys/user/info',
				success: ({resp : {code,data}}) => {
					if (code == '0') {
						this.userInfo = data;
					} 
				}
			});
		},
		methods: {

		}
	}
</script>

<style>
</style>
