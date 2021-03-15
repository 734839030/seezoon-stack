// 废弃
import axios from 'axios';
import router from '@/router';

import { message } from 'ant-design-vue';

// 创建 axios 实例
const request = axios.create({
  // API 请求的默认前缀
  baseURL: process.env.VUE_APP_API_BASE_URL,
  timeout: 6000, // 请求超时时间
  withCredentials: true,
  xsrfCookieName: 'XSRF-TOKEN',
  xsrfHeaderName: 'X-XSRF-TOKEN',
});

// 异常拦截处理器
const errorHandler = (error) => {
  if (error.response) {
    var status = error.response.status;
    if (status === 403) {
      message.error('没有权限，请联系管理员');
    } else if (status === 401) {
      console.log('登录态过期,请重新登录');
      router.push('/login');
    } else if (status === 500) {
      message.error('服务器开小差了，请稍后重试');
    } else if (status === 404) {
      message.error('请求地址不存在');
    } else {
      message.error(`未知错误status=${status}`);
    }
  } else {
    if (error.code === 'ECONNABORTED') {
      message.error('请求超时');
    } else {
      message.error(error.message);
    }
  }

  return Promise.reject(error);
};

// request interceptor
request.interceptors.request.use((config) => {
  return config;
}, errorHandler);

// response interceptor
request.interceptors.response.use((response) => {
  if (response && response.data) {
    var code = response.data.code;
    if (code.startsWith('80') || code.startsWith('90') || code === '-1') {
      // 系统错误直接提示即可，业务无需处理
      message.error(response.data.msg);
      return Promise.reject(response.data);
    }
  }
  return response.data;
}, errorHandler);

export default request;
