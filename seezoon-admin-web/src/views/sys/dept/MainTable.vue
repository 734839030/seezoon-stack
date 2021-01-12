<template>
  <a-space direction="vertical">
    <!-- 查询表单 -->
    <a-form ref="searchForm" :model="searchForm" layout="inline">
      <a-form-item label="名称" name="name">
        <a-input
            v-model:value="searchForm.name" :maxlength="100" placeholder="请输如名称">
        </a-input>
      </a-form-item>
      <a-form-item>
        <a-space>
          <a-button type="primary" @click="handleQueryPage()">查询</a-button>
          <a-button type="default" @click="this.$refs.searchForm.resetFields()">重置</a-button>
          <a-button type="default" @click="handleDataForm('添加')">添加</a-button>
        </a-space>
      </a-form-item>
    </a-form>
    <a-row :gutter="16">
      <a-col :span="6">
        <a-card size="small" title="部门">
          <a-tree :load-data="loadDeptData" :tree-data="deptTreeData" @select="onDeptTreeSelect"/>
        </a-card>
      </a-col>
      <a-col :span="18">
        <a-table :columns="columns" :data-source="data" :loading="loading" :pagination="pagination"
                 :row-key="(record) => record.id" :scroll="{y: 600 }" bordered size="small" @change="handleTableChange">
          <template #status="{ text }">
            <a-tag :color="text == 1 ? 'blue' : 'red'">
              {{ text == 1 ? "有效" : "无效" }}
            </a-tag>
          </template>
          <template #action="{ record }">
            <a @click="handleDataForm('编辑', record.id)">编辑</a>
            <a-divider type="vertical"/>
            <a-popconfirm placement="left" title="确定删除？" @confirm="handleDelete('/sys/dept/delete',record.id)">
              <a>删除</a>
            </a-popconfirm>
          </template>
        </a-table>
      </a-col>
    </a-row>
  </a-space>
  <data-form-modal ref="dataFormModal" :data-form="dataFormModal.dataForm" :title="dataFormModal.title"
                   @refreshDeptTree="this.loadDeptData" @refreshQueryPage="handleQueryPage"></data-form-modal>
</template>
<script>
import {pageTableMixin} from "@/views/common/mixins/page-table-mixin";
import DataFormModal from './DataFormModal';

export default {
  name: 'MainTable',
  components: {DataFormModal},
  mixins: [pageTableMixin],
  data() {
    return {
      url: '/sys/dept/query',
      columns: [
        {
          title: '部门名称',
          dataIndex: 'name',
        },
        {
          title: '联系人',
          dataIndex: 'contactUser',
        },
        {
          title: '联系电话',
          dataIndex: 'telephone',
        },
        {
          title: '序号',
          dataIndex: 'sort',
        },
        {
          title: '创建时间',
          dataIndex: 'createTime',
        },
        {
          title: '修改时间',
          dataIndex: 'updateTime',
        },
        {
          title: '操作',
          fixed: 'right',
          width: 120,
          slots: {customRender: 'action'},
        },
      ],
      dataFormModal: {},
      // 部门树
      deptTreeData: []
    }
  },
  mounted() {
    this.handleQueryPage();
    // 加载部门树
    this.loadDeptData()
  },
  methods: {
    handleDataForm(title, id) {
      if (id) {
        this.$http.get('/sys/dept/query/' + id).then(({data}) => {
          this.$refs.dataFormModal.show();
          this.dataFormModal = {title: title, dataForm: data};
        });
      } else {
        this.$refs.dataFormModal.show();
        this.dataFormModal = {title: title, dataForm: {sort: 10}};
      }
    },
    handleDeleteCb() {
      this.loadDeptData()
    },
    onDeptTreeSelect(selectedKeys) {
      this.searchForm.parentId = selectedKeys[0];
      this.handleQueryPage()
    },
    //加载部门树
    loadDeptData(treeNode) {
      console.log("treeNode", treeNode)
      return new Promise(resolve => {
        if (treeNode && treeNode.dataRef.children) {
          resolve();
          return;
        }
        this.$http.get(`/sys/dept/tree?parentId=${treeNode ? treeNode.eventKey : 0}`)
            .then(({data}) => {
              if (!treeNode) {
                this.deptTreeData = data;
              } else {
                treeNode.dataRef.children = data;
              }
              resolve();
              //this.deptTreeData = [...this.deptTreeData]
            });
      });
    }
  }
};
</script>