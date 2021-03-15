<template>
  <!-- 查询表单 -->
  <a-form
    ref="searchForm"
    :labelCol="this.labelCol"
    :model="searchForm"
    :wrapperCol="this.wrapperCol"
    layout="inline"
  >
    <a-form-item label="登录名" name="username">
      <a-input v-model:value="searchForm.username" :maxlength="50" placeholder="请输入登录名" />
    </a-form-item>
    <a-form-item label="姓名" name="name">
      <a-input v-model:value="searchForm.name" :maxlength="50" placeholder="请输入姓名" />
    </a-form-item>
    <a-form-item label="手机号" name="mobile">
      <a-input v-model:value="searchForm.mobile" :maxlength="20" placeholder="请输入手机号" />
    </a-form-item>
    <a-form-item label="状态" name="status">
      <a-select
        v-model:value="searchForm.status"
        :allowClear="true"
        placeholder="请选择状态"
        style="width: 120px"
      >
        <a-select-option :value="1">正常</a-select-option>
        <a-select-option :value="0">禁用</a-select-option>
      </a-select>
    </a-form-item>
    <a-form-item>
      <a-space>
        <a-button v-auth="'sys:user:query'" type="primary" @click="handleQuery()">查询</a-button>
        <a-button type="default" @click="this.$refs.searchForm.resetFields()">重置</a-button>
        <a-button v-auth="'sys:user:save'" type="default" @click="handleDataForm('添加')"
          >添加
        </a-button>
      </a-space>
    </a-form-item>
  </a-form>
  <a-row :gutter="16" class="mt-4">
    <a-col :span="4">
      <a-card size="small" title="部门">
        <a-tree :load-data="loadDeptData" :tree-data="deptTreeData" @select="onDeptTreeSelect" />
      </a-card>
    </a-col>
    <a-col :span="20" class="pr-4">
      <a-table
        :columns="columns"
        :data-source="data"
        :loading="loading"
        :pagination="pagination"
        :row-key="(record) => record.id"
        :scroll="{ x: 1500 }"
        bordered
        size="small"
        @change="handleTableChange"
      >
        <template #photoUrl="{ text }">
          <a-image v-if="text" :src="text" height="40px" width="40px" />
        </template>
        <template #status="{ text }">
          <a-tag :color="text == 1 ? 'blue' : 'red'">
            {{ text == 1 ? '有效' : '禁用' }}
          </a-tag>
        </template>
        <template #action="{ record }">
          <a v-auth="'sys:user:update'" @click="handleDataForm('编辑', record.id)">编辑</a>
          <a-divider type="vertical" />
          <a-popconfirm
            placement="left"
            title="确定删除？"
            @confirm="handleDelete('/sys/user/delete', record.id)"
          >
            <a v-auth="'sys:user:delete'">删除</a>
          </a-popconfirm>
        </template>
      </a-table>
    </a-col>
  </a-row>
  <data-form-modal
    ref="dataFormModal"
    :data-form="dataFormModal.dataForm"
    :title="dataFormModal.title"
    @refreshQuery="handleQuery"
  />
</template>
<script>
  import DataFormModal from './DataFormModal.vue';
  import { queryTableMixin } from '../../../mixins/common/query-table-mixin';
  import { deptTreeSelectMixin } from '../../../mixins/sys/dept-tree-select-mixin';
  import { defHttp } from '../../../utils/http/axios';

  export default {
    name: 'MainTable',
    components: { DataFormModal },
    mixins: [queryTableMixin, deptTreeSelectMixin],
    data() {
      return {
        url: '/sys/user/query',
        columns: [
          {
            title: '登录名',
            dataIndex: 'username',
            fixed: 'left',
            width: 100,
          },
          {
            title: '头像',
            dataIndex: 'photoUrl',
            slots: { customRender: 'photoUrl' },
            width: 100,
          },
          {
            title: '姓名',
            dataIndex: 'name',
            width: 100,
          },
          {
            title: '部门',
            dataIndex: 'deptName',
            width: 180,
          },
          {
            title: '手机号',
            dataIndex: 'mobile',
            width: 180,
          },
          {
            title: '邮箱',
            dataIndex: 'email',
          },
          {
            title: '状态',
            dataIndex: 'status',
            slots: { customRender: 'status' },
          },
          {
            title: '创建时间',
            dataIndex: 'createTime',
            width: 180,
          },
          {
            title: '修改时间',
            dataIndex: 'updateTime',
            width: 180,
            sorter: true,
          },
          {
            title: '操作',
            fixed: 'right',
            width: 100,
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
          defHttp.get({ url: '/sys/user/query/' + id }).then((data) => {
            this.$refs.dataFormModal.show();
            this.dataFormModal = { title: title, dataForm: data };
          });
        } else {
          this.$refs.dataFormModal.show();
          this.dataFormModal = { title: title, dataForm: { status: 1 } };
        }
      },
      onDeptTreeSelect(selectedKeys, { node }) {
        this.searchForm.deptId = node.selected ? undefined : node.dataRef.value;
        this.handleQuery();
      },
    },
  };
</script>
