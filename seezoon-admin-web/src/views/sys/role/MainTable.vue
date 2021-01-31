<template>
  <a-space direction="vertical">
    <!-- 查询表单 -->
    <a-form ref="searchForm" :model="searchForm" layout="inline">
      <a-form-item label="编码" name="code">
        <a-input
            v-model:value="searchForm.code" :maxlength="50" placeholder="角色编码">
        </a-input>
      </a-form-item>
      <a-form-item label="名称" name="name">
        <a-input
            v-model:value="searchForm.name" :maxlength="50" placeholder="请输入名称">
        </a-input>
      </a-form-item>
      <a-form-item label="数据范围" name="dataScope">
        <a-select
            v-model:value="searchForm.dataScope" :allowClear="true" :options="dataScopeArray" placeholder="请选择数据范围">
        </a-select>
      </a-form-item>
      <a-form-item label="状态" name="status">
        <a-select
            v-model:value="searchForm.status" :allowClear="true" placeholder="请选择状态">
          <a-select-option :value="1">启用</a-select-option>
          <a-select-option :value="0">停用</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item>
        <a-space>
          <a-button type="primary" @click="handleQuery()">查询</a-button>
          <a-button type="default" @click="this.$refs.searchForm.resetFields();">重置</a-button>
          <a-button type="default" @click="handleDataForm('添加')">添加</a-button>
        </a-space>
      </a-form-item>
    </a-form>
    <a-table :columns="columns" :data-source="data" :loading="loading" :pagination="pagination"
             :row-key="(record) => record.id" :scroll="{y: 600 }" bordered size="small"
             @change="handleTableChange">
      <template #status="{ text }">
        <a-tag :color="text == 1 ? 'blue' : 'red'">
          {{ text == 1 ? "启用" : "停用" }}
        </a-tag>
      </template>
      <template #action="{ record }">
        <a @click="this.$refs.roleAssignModal.show(record.id,record.name);">分配</a>
        <a-divider type="vertical"/>
        <a @click="handleDataForm('编辑', record.id)">编辑</a>
        <a-divider type="vertical"/>
        <a-popconfirm placement="left" title="确定删除？" @confirm="handleDelete('/sys/role/delete',record.id)">
          <a>删除</a>
        </a-popconfirm>
      </template>
    </a-table>
  </a-space>
  <data-form-modal ref="dataFormModal" :data-form="dataFormModal.dataForm" :title="dataFormModal.title"
                   @refreshQuery="handleQuery"></data-form-modal>
  <role-assign-modal ref="roleAssignModal"></role-assign-modal>

</template>
<script>
import {queryTableMixin} from "@/mixins/common/query-table-mixin";
import DataFormModal from './DataFormModal';
import {dataScopeArray, dataScopeMap} from "@/views/sys/role/data";
import RoleAssignModal from "@/views/sys/role/RoleAssignModal";

export default {
  name: 'MainTable',
  components: {DataFormModal, RoleAssignModal},
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
          customRender: function ({text}) {
            return dataScopeMap.get(text)
          }
        },
        {
          title: '状态',
          dataIndex: 'status',
          slots: {customRender: 'status'},
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
          width: 135,
          slots: {customRender: 'action'},
        },
      ],
      dataFormModal: {},
      dataScopeArray
    }
  },
  mounted() {
    this.handleQuery();
  },
  methods: {
    handleDataForm(title, id) {
      if (id) {
        this.$http.get('/sys/role/query/' + id).then(({data}) => {
          this.$refs.dataFormModal.show();
          this.dataFormModal = {title: title, dataForm: data};
        });
      } else {
        this.$refs.dataFormModal.show();
        this.dataFormModal = {title: title, dataForm: {status: 1}};
      }
    }
  }
};
</script>