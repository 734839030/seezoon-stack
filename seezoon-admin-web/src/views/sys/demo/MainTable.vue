<template>
  <!-- 查询表单 -->
  <a-form
    ref="searchForm"
    :labelCol="this.labelCol"
    :model="searchForm"
    :wrapperCol="this.wrapperCol"
    layout="inline"
  >
    <a-form-item label="文本" name="inputText">
      <a-input v-model:value="searchForm.inputText" :maxlength="50" placeholder="" />
    </a-form-item>
    <a-form-item label="下拉" name="inputSelect">
      <a-select
        v-model:value="searchForm.inputSelect"
        style="width: 120px"
        :allowClear="true"
        :options="inputSelectDicts"
        placeholder="请选择下拉数据"
      />
    </a-form-item>
    <a-form-item label="单选" name="inputRadio">
      <a-radio-group :options="inputRadioDicts" v-model:value="searchForm.inputRadio" />
    </a-form-item>
    <a-form-item label="多选" name="inputCheckbox">
      <a-checkbox-group
        v-model:value="searchForm.inputCheckboxTODO"
        :options="inputCheckboxDicts"
      />
    </a-form-item>
    <a-form-item label="整数" name="inputZhengshu">
      <a-input-number v-model:value="searchForm.inputZhengshu" :precision="0" />
    </a-form-item>
    <a-form-item label="小数" name="inputXiaoshu">
      <a-input-number v-model:value="searchForm.inputXiaoshu" :precision="2" />
    </a-form-item>
    <a-form-item label="日期" name="inputDateRange">
      <a-range-picker
        v-model:value="searchForm.inputDateRange"
        :allowClear="false"
        valueFormat="YYYY-MM-DD"
      />
    </a-form-item>
    <a-form-item>
      <a-space>
        <a-button v-auth="'sys:demo:query'" type="primary" @click="handleQuery()">查询</a-button>
        <a-button type="default" @click="this.$refs.searchForm.resetFields()">重置</a-button>
        <a-button v-auth="'sys:demo:save'" type="default" @click="handleDataForm('添加')"
          >添加
        </a-button>
      </a-space>
    </a-form-item>
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
    <template #view="{ record, text }">
      <a @click="this.$refs.dataViewModal.show(record.id)">{{ text ? text : '查看' }}</a>
    </template>
    <template #action="{ record }">
      <a v-auth="'sys:demo:update'" @click="handleDataForm('编辑', record.id)">编辑</a>
      <a-divider type="vertical" />
      <a-popconfirm
        placement="left"
        title="确定删除本部门及下级部门？"
        @confirm="handleDelete('/sys/demo/delete', record.id)"
      >
        <a v-auth="'sys:demo:delete'">删除</a>
      </a-popconfirm>
    </template>
  </a-table>
  <data-form-modal
    ref="dataFormModal"
    :data-form="dataFormModal.dataForm"
    :title="dataFormModal.title"
    @refreshQuery="handleQuery"
  />
  <data-view ref="dataViewModal" />
</template>
<script>
  import DataFormModal from './DataFormModal.vue';
  import DataView from './DataViewModal.vue';
  import { queryTableMixin } from '../../../mixins/common/query-table-mixin';
  import { defHttp } from '../../../utils/http/axios';
  import {
    inputCheckboxDicts,
    inputRadioDicts,
    inputRadioDictsMap,
    inputSelectDicts,
    inputSelectDictsMap,
  } from './data';
  import moment from 'moment';

  export default {
    name: 'MainTable',
    components: { DataFormModal, DataView },
    mixins: [queryTableMixin],
    setup() {
      return { inputSelectDicts, inputRadioDicts, inputCheckboxDicts };
    },
    data() {
      return {
        searchForm: {
          inputDateRange: [moment().day(-7).format('YYYY-MM-DD'), moment().format('YYYY-MM-DD')],
        },
        url: '/sys/demo/query',
        columns: [
          {
            title: '文本',
            dataIndex: 'inputText',
            slots: { customRender: 'view' },
          },
          {
            title: '下拉',
            dataIndex: 'inputSelect',
            ellipsis: true,
            customRender: function ({ text }) {
              return inputSelectDictsMap.get(text);
            },
          },
          {
            title: '单选',
            dataIndex: 'inputRadio',
            ellipsis: true,
            customRender: function ({ text }) {
              return inputRadioDictsMap.get(text);
            },
          },
          {
            title: '文本域',
            dataIndex: 'inputTextarea',
            ellipsis: true,
          },
          {
            title: '日期',
            dataIndex: 'inputDate',
            ellipsis: true,
          },
          {
            title: '整数',
            dataIndex: 'inputZhengshu',
            ellipsis: true,
          },
          {
            title: '小数',
            dataIndex: 'inputXiaoshu',
            ellipsis: true,
          },
          {
            title: '创建时间',
            dataIndex: 'createTime',
            ellipsis: true,
            sorter: true,
          },
          {
            title: '修改时间',
            dataIndex: 'updateTime',
            ellipsis: true,
          },
          {
            title: '操作',
            fixed: 'right',
            width: 120,
            slots: { customRender: 'action' },
          },
        ],
        dataFormModal: {},
      };
    },
    mounted() {
      this.handleQuery();
    },
    methods: {
      handleDataForm(title, id) {
        if (id) {
          defHttp.get({ url: '/sys/demo/query/' + id }).then((data) => {
            this.$refs.dataFormModal.show();
            this.dataFormModal = { title: title, dataForm: data };
          });
        } else {
          this.$refs.dataFormModal.show();
          this.dataFormModal = { title: title, dataForm: { sort: 1000 } };
        }
      },
    },
  };
</script>
