<template>
  <!-- 查询表单 -->
  <a-form
    ref="searchForm"
    :labelCol="this.labelCol"
    :model="searchForm"
    :wrapperCol="this.wrapperCol"
    layout="inline"
  >
    <a-form-item label="类型" name="type">
      <a-select
        v-model:value="searchForm.type"
        :allowClear="true"
        :options="dictTypes"
        placeholder="请选择类型"
        show-search
        style="width: 160px"
      />
    </a-form-item>

    <a-form-item label="编码" name="code">
      <a-input v-model:value="searchForm.code" :maxlength="50" placeholder="请输入编码" />
    </a-form-item>
    <a-form-item label="名称" name="name">
      <a-input v-model:value="searchForm.name" :maxlength="50" placeholder="请输入名称" />
    </a-form-item>
    <a-form-item>
      <a-space>
        <a-button v-auth="'sys:dict:query'" type="primary" @click="handleQuery()">查询</a-button>
        <a-button type="default" @click="this.$refs.searchForm.resetFields()">重置</a-button>
        <a-button v-auth="'sys:dict:save'" type="default" @click="handleDataForm('添加')"
          >添加</a-button
        >
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
    class="mt-4 pr-4"
    size="small"
    @change="handleTableChange"
  >
    <template #status="{ text }">
      <a-tag :color="text == 1 ? 'blue' : 'red'">
        {{ text == 1 ? '有效' : '无效' }}
      </a-tag>
    </template>
    <template #action="{ record }">
      <a v-auth="'sys:dict:update'" @click="handleDataForm('编辑', record.id)">编辑</a>
      <a-divider type="vertical" />
      <a-popconfirm
        placement="left"
        title="确定删除？"
        @confirm="handleDelete('/sys/dict/delete', record.id)"
      >
        <a v-auth="'sys:dict:delete'">删除</a>
      </a-popconfirm>
    </template>
  </a-table>
  <data-form-modal
    ref="dataFormModal"
    :data-form="dataFormModal.dataForm"
    :title="dataFormModal.title"
    @refreshQuery="handleQuery"
  />
</template>
<script>
  import DataFormModal from './DataFormModal.vue';
  import { onMounted, ref } from 'vue';
  import { defHttp } from '../../../utils/http/axios';
  import { queryTableMixin } from '../../../mixins/common/query-table-mixin.js';
  import { getTypes } from '../../../api/sys';

  export default {
    name: 'MainTable',
    components: { DataFormModal },
    mixins: [queryTableMixin],
    setup() {
      let dictTypes = ref([]);
      onMounted(async () => {
        dictTypes.value = await getTypes();
      });
      return {
        dictTypes,
      };
    },
    data() {
      return {
        url: '/sys/dict/query',
        columns: [
          {
            title: '类型',
            dataIndex: 'type',
            width: 120,
          },
          {
            title: '编码',
            dataIndex: 'code',
            width: 120,
          },
          {
            title: '名称',
            dataIndex: 'name',
            width: 100,
          },
          {
            title: '排序',
            dataIndex: 'sort',
            width: 100,
          },
          {
            title: '状态',
            dataIndex: 'status',
            width: 100,
            slots: { customRender: 'status' },
          },
          {
            title: '创建时间',
            dataIndex: 'createTime',
          },
          {
            title: '修改时间',
            dataIndex: 'updateTime',
            sorter: true,
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
          defHttp.get({ url: '/sys/dict/query/' + id }).then((data) => {
            this.$refs.dataFormModal.show();
            this.dataFormModal = { title: title, dataForm: data };
          });
        } else {
          this.$refs.dataFormModal.show();
          this.dataFormModal = {
            title: title,
            dataForm: { status: 1, sort: 10, type: this.searchForm.type },
          };
        }
      },
    },
  };
</script>
