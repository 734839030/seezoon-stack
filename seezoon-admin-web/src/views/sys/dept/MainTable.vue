<template>
  <!-- 查询表单 -->
  <a-form
    ref="searchForm"
    :model="searchForm"
    layout="inline"
    :labelCol="this.labelCol"
    :wrapperCol="this.wrapperCol"
  >
    <a-form-item label="名称" name="name">
      <a-input v-model:value="searchForm.name" :maxlength="100" placeholder="请输如名称" />
    </a-form-item>
    <a-form-item>
      <a-space>
        <a-button type="primary" @click="handleQuery()">查询</a-button>
        <a-button type="default" @click="this.$refs.searchForm.resetFields()">重置</a-button>
        <a-button type="default" @click="handleDataForm('添加')">添加</a-button>
      </a-space>
    </a-form-item>
  </a-form>
  <a-row :gutter="16" class="mt-4">
    <a-col :span="4">
      <a-card size="small" title="部门">
        <a-tree :load-data="loadDeptData" :tree-data="deptTreeData" @select="onDeptTreeSelect" />
      </a-card>
    </a-col>
    <a-col :span="20">
      <a-table
        :columns="columns"
        :data-source="data"
        :loading="loading"
        :pagination="pagination"
        :row-key="(record) => record.id"
        :scroll="{ x: 1200 }"
        bordered
        size="small"
        @change="handleTableChange"
      >
        <template #action="{ record }">
          <a @click="handleDataForm('编辑', record.id)">编辑</a>
          <a-divider type="vertical" />
          <a-popconfirm
            placement="left"
            title="确定删除本部门及下级部门？"
            @confirm="handleDelete('/sys/dept/delete', record.id)"
          >
            <a>删除</a>
          </a-popconfirm>
        </template>
      </a-table>
    </a-col>
  </a-row>
  <data-form-modal
    ref="dataFormModal"
    :data-form="dataFormModal.dataForm"
    :title="dataFormModal.title"
    @refreshDeptTree="this.loadDeptData"
    @refreshQuery="handleQuery"
  />
</template>
<script>
  import DataFormModal from './DataFormModal.vue';
  import { queryTableMixin } from '../../../mixins/common/query-table-mixin';
  import { deptTree } from '../../../api/sys';
  import { defHttp } from '../../../utils/http/axios';

  export default {
    name: 'MainTable',
    components: { DataFormModal },
    mixins: [queryTableMixin],
    data() {
      return {
        url: '/sys/dept/query',
        columns: [
          {
            title: '部门名称',
            dataIndex: 'name',
            fixed: 'left',
            width: 180,
          },
          {
            title: '父部门',
            dataIndex: 'parentName',
            width: 180,
          },
          {
            title: '联系人',
            dataIndex: 'contactUser',
            width: 120,
          },
          {
            title: '联系电话',
            dataIndex: 'telephone',
            width: 180,
          },
          {
            title: '序号',
            dataIndex: 'sort',
            width: 80,
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
          },
          {
            title: '操作',
            fixed: 'right',
            width: 120,
            slots: { customRender: 'action' },
          },
        ],
        dataFormModal: {},
        // 部门树
        deptTreeData: [],
      };
    },
    mounted() {
      this.handleQuery();
      // 加载部门树
      this.loadDeptData();
    },
    methods: {
      handleDataForm(title, id) {
        this.$refs.dataFormModal.loadDeptData();
        if (id) {
          defHttp.get({ url: '/sys/dept/query/' + id }).then((data) => {
            this.$refs.dataFormModal.show();
            if (data.parentId === 0) {
              data.parentId = undefined;
            }
            this.dataFormModal = { title: title, dataForm: data };
          });
        } else {
          this.$refs.dataFormModal.show();
          this.dataFormModal = { title: title, dataForm: { sort: 10 } };
        }
      },
      handleDeleteCb(id) {
        this.searchForm.parentId =
          id === this.searchForm.parentId ? null : this.searchForm.parentId;
        this.loadDeptData();
      },
      onDeptTreeSelect(selectedKeys, { node }) {
        this.searchForm.parentId = node.selected ? undefined : node.dataRef.value;
        this.handleQuery();
      },
      //加载部门树
      loadDeptData(treeNode) {
        return new Promise((resolve) => {
          if (treeNode && treeNode.dataRef.children) {
            resolve();
            return;
          }
          deptTree(treeNode ? treeNode.dataRef.value : 0).then((data) => {
            if (!treeNode) {
              this.deptTreeData = data;
            } else {
              treeNode.dataRef.children = data;
            }
            resolve();
          });
        });
      },
    },
  };
</script>
