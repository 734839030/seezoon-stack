import {
	getBaseUrl
} from './env.js'
import {
	getXsrfToken,
	getLoginCookie
} from './login.js'

export const defHttp = {
	post: function(args) {
		args.method = 'POST';
		uni.request(args);
	},
	get: function(args) {
		args.method = 'GET';
		uni.request(args);
	},
	postForm: function(args) {
		args.method = 'POST';
		if (!args.header) {
			args.header = {};
		}
		args.header['content-type'] = 'application/x-www-form-urlencoded';
		uni.request(args);
	}
}

export function requestInterceptor() {
	uni.addInterceptor('request', {
		timeout: 10000,
		withCredentials: true,
		invoke(args) {
			args.url = getBaseUrl() + args.url
			if (!args.header) {
				args.header = {};
			}
			//#ifndef H5
			args.header['Cookie'] = getLoginCookie();
			//#endif
			args.header['X-XSRF-TOKEN'] = getXsrfToken();
		},
		success(args) {
			if (args.statusCode == 401) {
				uni.redirectTo({
					url: '/pages/login/index'
				})
			} else {
				args.resp = args.data;
				args.data = null;
			}
		},
		fail(err) {
			console.log('interceptor-fail', err)
			if (err.statusCode == 403) {
				uni.showToast({
					icon: 'error',
					title: '没有权限'
				})
			} else if (err.statusCode == 404) {
				uni.showToast({
					icon: 'error',
					title: '路径错误'
				})
			} else if (err.statusCode == 500) {
				uni.showToast({
					icon: 'error',
					title: '服务器故障'
				})
			} else {
				uni.showToast({
					icon: 'error',
					title: `${err.errMsg}`
				})
			}
		},
	})
}
