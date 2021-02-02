<template>
  <a-space direction="vertical">
    <a-form layout="inline">
      <a-form-item>
        <a-space>
          <a-button type="primary" @click="handleQuery()">查询</a-button>
          <a-button type="default" @click="this.$refs.searchForm.resetFields();">重置</a-button>
          <a-button type="default" @click="handleDataForm('添加')">添加</a-button>
        </a-space>
      </a-form-item>
    </a-form>
    <a-table :columns="columns" :data-source="data" :loading="loading"
             :row-key="(record) => record.id" :scroll="{y: 600 }" bordered size="small">
      <template #type="{ text }">
        <a-tag v-if="text === 1" color="blue">
          目录
        </a-tag>
        <a-tag v-else-if="text === 2" color="cyan">
          菜单
        </a-tag>
        <!-- type==3-->
        <a-tag v-else color="green">
          按钮
        </a-tag>
      </template>
      <template #status="{ text }">
        <a-tag :color="text == 1 ? 'blue' : 'red'">
          {{ text == 1 ? "启用" : "停用" }}
        </a-tag>
      </template>
      <template #action="{ record }">
        <a @click="handleDataForm('编辑', record.id)">编辑</a>
        <a-divider type="vertical"/>
        <a-popconfirm :title="record.children ? '子节点将一起删除，确定删除?':'确定删除?'" placement="left"
                      @confirm="handleDelete('/sys/menu/delete',record.id)">
          <a>删除</a>
        </a-popconfirm>
      </template>
    </a-table>
  </a-space>
  <data-form-modal ref="dataFormModal" :data-form="dataFormModal.dataForm" :title="dataFormModal.title"
                   @refreshQuery="handleQuery"></data-form-modal>
</template>
<script>
import {queryTableMixin} from "@/mixins/common/query-table-mixin";
import DataFormModal from './DataFormModal';

export default {
  name: 'MainTable',
  components: {DataFormModal},
  mixins: [queryTableMixin],
  data() {
    return {
      url: '/sys/menu/query',
      columns: [
        {
          title: '名称',
          dataIndex: 'name',
        },
        {
          title: '地址',
          dataIndex: 'url',
        },
        {
          title: '图标',
          dataIndex: 'icon',
        },
        {
          title: '类型',
          dataIndex: 'type',
          slots: {customRender: 'type'},
        },
        {
          title: '排序',
          dataIndex: 'sort',
        },
        {
          title: '授权标识',
          dataIndex: 'permission',
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
    this.handleQuery();
  },
  methods: {
    handleDataForm(title, id) {
      if (id) {
        this.$http.get('/sys/menu/query/' + id).then(({data}) => {
          this.$refs.dataFormModal.show();
          this.dataFormModal = {title: title, dataForm: data};
        });
      } else {
        this.$refs.dataFormModal.show();
        this.dataFormModal = {title: title, dataForm: {status: 1, type: 1, sort: 10}};
      }

    }
  }
};
</script>