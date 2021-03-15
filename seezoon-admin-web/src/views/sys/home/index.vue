<template>
  <div class="welcome">
    <!--<House />-->
    <demo-home />
  </div>
</template>
<script lang="ts">
  import { defineComponent } from 'vue';
  //import House from './house/index.vue';
  import DemoHome from '../../demo/dashboard/analysis/index.vue';

  export default defineComponent({
    name: 'Welcome',
    // components: { House },
    components: { DemoHome },
  });
</script>
<style lang="less" scoped>
  .welcome {
    display: flex;
    width: 100%;
    height: 100%;
    justify-content: center;
    align-items: center;
  }
</style>
