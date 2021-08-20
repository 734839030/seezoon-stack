let LOGIN_COOKIE_KEY = 'loginCookieKey';
let XSRF_TOKEN_KEY = 'xsrfTokenKey';

/**
 * 针对小程序类需要自动处理cookie
 * 
 * @param {Object} cookies
 */
export function setLoginResponseData(cookies) {
	console.log('cookies:', cookies);
	let loginCookie = '';
	cookies.forEach(value => {
		if (value.startsWith('SESSION=') || value.startsWith('remember-me=')) {
			loginCookie = loginCookie + value + ';';
		}
		if (value.startsWith('XSRF-TOKEN=')) {
			const xsrfToken = value.substring(11, value.indexOf(';'));
			console.log('xsrf-token:', xsrfToken);
			uni.setStorageSync(XSRF_TOKEN_KEY, xsrfToken);
		}
	});
	console.log('loginCookie:', loginCookie);
	uni.setStorageSync(LOGIN_COOKIE_KEY, loginCookie);
}

export function getLoginCookie() {
	return uni.getStorageSync(LOGIN_COOKIE_KEY);
}

export function getXsrfToken() {
	return uni.getStorageSync(XSRF_TOKEN_KEY);
}