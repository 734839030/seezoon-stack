<template>
  <page-wrapper contentBackground contentClass="p-4">
    <main-table />
  </page-wrapper>
</template>
<script>
  import MainTable from './MainTable.vue';
  import { PageWrapper } from '../../../components/Page';

  export default {
    name: 'SysDemo',
    components: { MainTable, PageWrapper },
  };
</script>
