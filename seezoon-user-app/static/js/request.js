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
		invoke(args) {
			args.timeout = args.timeout ? args.timeout : 10000
			args.withCredentials = true; 
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
				const  pages = getCurrentPages();
				// 获取当前页方便登录后跳回
				let curPage = pages[pages.length-1].$page.fullPath;
				uni.redirectTo({
					url: '/pages/login/index?goPage=' + encodeURIComponent(curPage)
				})
				return false;
			} else {
				args.resp = args.data;
				args.data = null;
				const {code,msg} = args.resp;
				if (code != '0') {
					uni.showToast({
						icon:'none',
						title:msg
					})
				}
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
