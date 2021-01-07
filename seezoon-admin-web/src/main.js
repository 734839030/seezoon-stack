import { createApp } from 'vue'
import App from './App.vue'
// antd 
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/antd.css';
import request from './utils/request'
import VueAxios from 'vue-axios'

import router from './router'


const app = createApp(App);
app.config.productionTip = false;
// see https://github.com/imcvampire/vue-axios#readme  
app.use(VueAxios,request)

app.use(router) 
app.use(Antd)
app.mount('#app')
