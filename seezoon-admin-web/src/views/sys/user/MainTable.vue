<template>
  <a-space direction="vertical">
    <!-- 查询表单 -->
    <a-form ref="searchForm" :model="searchForm" layout="inline">
      <a-form-item label="登录名" name="username">
        <a-input
            v-model:value="searchForm.username" :maxlength="50" placeholder="请输入登录名">
        </a-input>
      </a-form-item>
      <a-form-item label="手机号" name="mobile">
        <a-input
            v-model:value="searchForm.mobile" :maxlength="20" placeholder="请输入手机号">
        </a-input>
      </a-form-item>
      <a-form-item label="状态" name="status">
        <a-select
            v-model:value="searchForm.status" :allowClear="true" placeholder="请选择状态">
          <a-select-option :value="1">正常</a-select-option>
          <a-select-option :value="0">禁用</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item>
        <a-space>
          <a-button type="primary" @click="handleQueryPage()">查询</a-button>
          <a-button type="default" @click="this.$refs.searchForm.resetFields();">重置</a-button>
          <a-button type="default" @click="handleDataForm('添加')">添加</a-button>
        </a-space>
      </a-form-item>
    </a-form>
    <a-row :gutter="16">
      <a-col :span="4">
        <a-card size="small" title="部门">
          <a-tree :load-data="loadDeptData" :tree-data="deptTreeData" @select="onDeptTreeSelect"/>
        </a-card>
      </a-col>
      <a-col :span="20">
        <a-table :columns="columns" :data-source="data" :loading="loading" :pagination="pagination"
                 :row-key="(record) => record.id"
                 :scroll="{y: 600 }" bordered size="small" @change="handleTableChange">
          <template #status="{ text }">
            <a-tag :color="text == 1 ? 'blue' : 'red'">
              {{ text == 1 ? "有效" : "禁用" }}
            </a-tag>
          </template>
          <template #action="{ record }">
            <a @click="handleDataForm('编辑', record.id)">编辑</a>
            <a-divider type="vertical"/>
            <a-popconfirm placement="left" title="确定删除？" @confirm="handleDelete('/sys/user/delete',record.id)">
              <a>删除</a>
            </a-popconfirm>
          </template>
        </a-table>
      </a-col>
    </a-row>
  </a-space>
  <data-form-modal ref="dataFormModal" :data-form="dataFormModal.dataForm" :title="dataFormModal.title"
                   @refreshQueryPage="handleQueryPage"></data-form-modal>
</template>
<script>
import {pageTableMixin} from "@/mixins/common/page-table-mixin";
import DataFormModal from './DataFormModal';
import {deptTreeSelectMixin} from "@/mixins/sys/dept-tree-select-mixin";

export default {
  name: 'MainTable',
  components: {DataFormModal},
  mixins: [pageTableMixin, deptTreeSelectMixin],
  data() {
    return {
      url: '/sys/user/query',
      columns: [
        {
          title: '登录名',
          dataIndex: 'username',
        },
        {
          title: '头像',
          dataIndex: 'photo',
        },
        {
          title: '姓名',
          dataIndex: 'name',
        },
        {
          title: '部门',
          dataIndex: 'deptName',
        },
        {
          title: '手机号',
          dataIndex: 'mobile',
        },
        {
          title: '邮箱',
          dataIndex: 'email',
        },
        {
          title: '状态',
          dataIndex: 'status',
          slots: {customRender: 'status'},
        },
        {
          title: '创建时间',
          dataIndex: 'createTime',
          ellipsis: true
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
          slots: {customRender: 'action'},
        },
      ],
      dataFormModal: {}
    }
  },
  mounted() {
    this.handleQueryPage();
  },
  methods: {
    handleDataForm(title, id) {
      if (id) {
        this.$http.get('/sys/user/query/' + id).then(({data}) => {
          this.$refs.dataFormModal.show();
          this.dataFormModal = {title: title, dataForm: data};
        });
      } else {
        this.$refs.dataFormModal.show();
        this.dataFormModal = {title: title, dataForm: {status: 1}};
      }
    },
    onDeptTreeSelect(selectedKeys, {node}) {
      this.searchForm.deptId = node.selected ? undefined : node.dataRef.value;
      this.handleQueryPage()
    },
  }
};
</script>