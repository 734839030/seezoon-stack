<template>
  <!--  <pro-layout>-->
  <a-config-provider :locale="locale">
    <router-view></router-view>
  </a-config-provider>
  <!--  </pro-layout>-->
</template>
<script>
import zhCN from 'ant-design-vue/es/locale/zh_CN';
// import ProLayout, {PageContainer} from '@ant-design-vue/pro-layout';

export default {
  name: 'App',
  // components: {ProLayout, PageContainer},
  data() {
    return {
      locale: zhCN
    }
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  /*text-align: center;*/
  color: #2c3e50;
  /*margin-top: 20px;*/
}
</style>
