<template>
  <!-- 查询表单 -->
  <a-form
    ref="searchForm"
    :labelCol="this.labelCol"
    :model="searchForm"
    :wrapperCol="this.wrapperCol"
    labelAlign="left"
    layout="inline"
  >
    <a-row>
      <a-col :md="5" :xs="24">
        <a-form-item label="登录账号" name="userName">
          <a-input v-model:value="searchForm.userName" :maxlength="50" placeholder="" />
        </a-form-item>
      </a-col>
      <a-col :md="5" :xs="24">
        <a-form-item label="登录结果" name="result">
          <a-select
            v-model:value="searchForm.result"
            :allowClear="true"
            :options="resultDicts"
            placeholder="请选择"
            style="width: 140px"
          />
        </a-form-item>
      </a-col>
      <a-col :md="8" :xs="24">
        <a-form-item label="登录时间" name="loginDateRange">
          <a-range-picker
            v-model:value="searchForm.loginDateRange"
            :allowClear="false"
            valueFormat="YYYY-MM-DD"
            style="width: 220px"
          />
        </a-form-item>
      </a-col>
      <a-col :md="6" :xs="24">
        <a-form-item label="IP地址" name="ip">
          <a-input v-model:value="searchForm.ip" :maxlength="16" placeholder="" />
        </a-form-item>
      </a-col>
    </a-row>
    <a-row>
      <a-col :md="6" :xs="24">
        <a-form-item>
          <a-space>
            <a-button v-auth="'sys:login_log:query'" type="primary" @click="handleQuery()"
              >查询</a-button
            >
            <a-button type="default" @click="this.$refs.searchForm.resetFields()">重置</a-button>
          </a-space>
        </a-form-item>
      </a-col>
    </a-row>
  </a-form>
  <a-table
    :columns="columns"
    :data-source="data"
    :loading="loading"
    :pagination="pagination"
    :row-key="(record) => record.id"
    bordered
    class="mt-4"
    size="small"
    @change="handleTableChange"
  >
    <template #area="{ record, text }">
      <a @click="openWindow('https://ip.tool.chinaz.com/' + record.ip)">{{
        text ? text : '查看'
      }}</a>
    </template>
    <template #result="{ text }">
      <a-tag :color="text == 0 ? 'blue' : 'red'">
        {{ resultDictsMap.get(text) }}
      </a-tag>
    </template>
    <template #action="{ record }">
      <a-popconfirm
        placement="left"
        title="确定删除？"
        @confirm="handleDelete('/sys/login_log/delete', record.id)"
      >
        <a v-auth="'sys:login_log:delete'">删除</a>
      </a-popconfirm>
    </template>
  </a-table>
</template>
<script>
  import { queryTableMixin } from '../../../mixins/common/query-table-mixin';
  import { resultDicts, resultDictsMap } from './data.ts';
  import { openWindow } from '../../../utils';
  import moment from 'moment';

  export default {
    name: 'MainTable',
    mixins: [queryTableMixin],
    setup() {
      return { resultDicts, resultDictsMap, openWindow };
    },
    data() {
      return {
        searchForm: {
          loginDateRange: [moment().day(-7).format('YYYY-MM-DD'), moment().format('YYYY-MM-DD')],
        },
        url: '/sys/login_log/query',
        columns: [
          {
            title: '账号',
            dataIndex: 'userName',
            ellipsis: true,
          },
          {
            title: '登录结果',
            dataIndex: 'result',
            width: 120,
            ellipsis: true,
            slots: { customRender: 'result' },
          },
          {
            title: '登录时间',
            dataIndex: 'loginTime',
            width: 180,
            ellipsis: true,
            sorter: true,
          },
          {
            title: 'IP地址',
            dataIndex: 'ip',
            width: 140,
            ellipsis: true,
          },
          {
            title: '地区',
            dataIndex: 'area',
            width: 60,
            slots: { customRender: 'area' },
          },
          {
            title: '用户代理',
            dataIndex: 'userAgent',
            ellipsis: true,
          },
          {
            title: '设备名称',
            dataIndex: 'deviceName',
            ellipsis: true,
            width: 200,
          },
          {
            title: '浏览器名称',
            dataIndex: 'browserName',
            ellipsis: true,
            width: 200,
          },
          {
            title: '操作',
            fixed: 'right',
            width: 60,
            slots: { customRender: 'action' },
          },
        ],
      };
    },
    mounted() {
      this.handleQuery();
    },
  };
</script>
