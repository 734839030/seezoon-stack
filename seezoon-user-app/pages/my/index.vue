<template>
	<view class="container">
		<view class="intro">{{intro}}</view>
		<text class="intro">1详见：</text>
	</view>
</template>

<script>
	export default {
		setup(){
			const intro = "hello world";

			return {intro};
		},
		data() {
		
			return {
			}
		},
		methods: {

		}
	}
</script>

<style>
	.container {
		padding: 20px;
		font-size: 14px;
		line-height: 24px;
	}
</style>
