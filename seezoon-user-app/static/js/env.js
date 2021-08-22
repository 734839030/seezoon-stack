export  function getBaseUrl() {
	if (process.env.NODE_ENV === 'development') {
		//#ifndef H5
		return 'http://localhost:8080';
		//#endif
		
		//#ifdef H5
		// /api 开发环境走代理，解决跨越问题，后台配置允许跨域，但chrome要求是https 并且cookie 为secure,目前测试uniapp vue3 vite 版本代理无效，先试用hbuilder 内置浏览器测试
		//return '';
		return 'http://localhost:8080'
		//#endif
		
	} else if (process.env.NODE_ENV === 'production') {
		return 'https://stack.seezoon.com/api';
	}
}