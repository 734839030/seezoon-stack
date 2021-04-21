<template>
  <!-- 查询表单 -->
  <a-form
    ref="searchForm"
    :labelCol="this.labelCol"
    :model="searchForm"
    :wrapperCol="this.wrapperCol"
    layout="inline"
  >
    <a-form-item label="编码" name="code">
      <a-input v-model:value="searchForm.code" :maxlength="50" placeholder="角色编码" />
    </a-form-item>
    <a-form-item label="名称" name="name">
      <a-input v-model:value="searchForm.name" :maxlength="50" placeholder="请输入名称" />
    </a-form-item>
    <a-form-item label="数据范围" name="dataScope">
      <a-select
        v-model:value="searchForm.dataScope"
        :allowClear="true"
        :options="dataScopeArray"
        placeholder="请选择"
        style="width: 130px"
      />
    </a-form-item>
    <a-form-item label="状态" name="status">
      <a-select
        v-model:value="searchForm.status"
        :allowClear="true"
        placeholder="请选择状态"
        style="width: 120px"
      >
        <a-select-option :value="1">启用</a-select-option>
        <a-select-option :value="0">停用</a-select-option>
      </a-select>
    </a-form-item>
    <a-form-item>
      <a-space>
        <a-button v-auth="'sys:role:query'" type="primary" @click="handleQuery()">查询</a-button>
        <a-button type="default" @click="this.$refs.searchForm.resetFields()">重置</a-button>
        <a-button
          v-auth="'sys:role:save'"
          type="default"
          @click="this.$refs.dataFormModal.open('添加')"
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
    <template #status="{ text }">
      <a-tag :color="text == 1 ? 'blue' : 'red'">
        {{ text == 1 ? '启用' : '停用' }}
      </a-tag>
    </template>
    <template #action="{ record }">
      <a v-auth="'sys:role:assign'" @click="this.$refs.roleAssignModal.open(record.id, record.name)"
        >分配</a
      >
      <a-divider type="vertical" />
      <a v-auth="'sys:role:update'" @click="this.$refs.dataFormModal.open('编辑', record.id)"
        >编辑</a
      >
      <a-divider type="vertical" />
      <a-popconfirm
        placement="left"
        title="确定删除？"
        @confirm="handleDelete('/sys/role/delete', record.id)"
      >
        <a v-auth="'sys:role:delete'">删除</a>
      </a-popconfirm>
    </template>
  </a-table>
  <data-form-modal ref="dataFormModal" @refreshQuery="handleQuery" />
  <role-assign-modal ref="roleAssignModal" />
</template>
<script>
  import DataFormModal from './DataFormModal.vue';
  import { queryTableMixin } from '../../../mixins/common/query-table-mixin';
  import RoleAssignModal from './RoleAssignModal.vue';
  import { dataScopeArray, dataScopeMap } from './data';

  export default {
    name: 'MainTable',
    components: { DataFormModal, RoleAssignModal },
    mixins: [queryTableMixin],
    data() {
      return {
        url: '/sys/role/query',
        columns: [
          {
            title: '编码',
            dataIndex: 'code',
          },
          {
            title: '名称',
            dataIndex: 'name',
          },
          {
            title: '数据范围',
            dataIndex: 'dataScope',
            customRender: function ({ text }) {
              return dataScopeMap.get(text);
            },
          },
          {
            title: '状态',
            dataIndex: 'status',
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
            width: 140,
            slots: { customRender: 'action' },
          },
        ],
        dataScopeArray,
      };
    },
    mounted() {
      this.handleQuery();
    },
  };
</script>
