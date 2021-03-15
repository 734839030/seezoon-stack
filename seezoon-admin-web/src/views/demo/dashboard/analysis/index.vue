<template>
  <div class="p-4 analysis">
    <a-row class="pl-2">
      <template v-for="item in growCardList" :key="item.title">
        <a-col :lg="6" :md="12" :sm="24">
          <GrowCard :info="item" />
        </a-col>
      </template>
    </a-row>

    <a-row>
      <a-col :lg="17" :md="24" class="my-3">
        <CollapseContainer :canExpan="false" class="mr-3" title="产品成交额">
          <AnalysisLine />
        </CollapseContainer>
        <a-row class="mt-3">
          <a-col :lg="12" :md="24" class="product-total">
            <CollapseContainer :canExpan="false" class="mr-3" title="产品成交额">
              <AnalysisPie />
            </CollapseContainer>
          </a-col>
          <a-col :lg="12" :md="24">
            <CollapseContainer :canExpan="false" class="mr-3" title="用户来源">
              <AnalysisBar />
            </CollapseContainer>
          </a-col>
        </a-row>
      </a-col>
      <a-col :lg="7" :md="24">
        <CollapseContainer :canExpan="false" class="mt-3" title="项目进度">
          <template v-for="item in taskList" :key="item.title">
            <TaskCard :info="item" />
          </template>
        </CollapseContainer>
      </a-col>
    </a-row>
    <a-row>
      <FlowAnalysis />
    </a-row>
  </div>
</template>
<script lang="ts">
  import { defineComponent } from 'vue';
  import GrowCard from './components/GrowCard.vue';
  import AnalysisLine from './components/AnalysisLine.vue';
  import AnalysisPie from './components/AnalysisPie.vue';
  import AnalysisBar from './components/AnalysisBar.vue';
  import TaskCard from './components/TaskCard.vue';
  import FlowAnalysis from './components/FlowAnalysis';
  import { CollapseContainer } from '/@/components/Container';
  import { Col, Row } from 'ant-design-vue';
  import { growCardList, taskList } from './data';

  export default defineComponent({
    name: 'DemoHome',
    components: {
      GrowCard,
      CollapseContainer,
      AnalysisLine,
      AnalysisPie,
      AnalysisBar,
      TaskCard,
      FlowAnalysis,
      [Row.name]: Row,
      [Col.name]: Col,
    },
    setup() {
      return { growCardList, taskList };
    },
  });
</script>
<style lang="less" scoped>
  .analysis {
    width: 100%;
  }
</style>
