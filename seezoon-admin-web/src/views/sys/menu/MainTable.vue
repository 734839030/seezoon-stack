<template>
  <a-form layout="inline">
    <a-form-item>
      <a-space>
        <a-button type="primary" @click="handleQuery()">查询</a-button>
        <a-button type="default" @click="this.$refs.searchForm.resetFields()">重置</a-button>
        <a-button type="default" @click="handleDataForm('添加')">添加</a-button>
      </a-space>
    </a-form-item>
  </a-form>
  <a-table
    :columns="columns"
    :data-source="data"
    :loading="loading"
    :row-key="(record) => record.id"
    :scroll="{ x: 1200 }"
    class="mt-4 pr-4"
    bordered
    size="small"
  >
    <template #type="{ text }">
      <a-tag v-if="text === 1" color="blue"> 目录 </a-tag>
      <a-tag v-else-if="text === 2" color="cyan"> 菜单 </a-tag>
      <!-- type==3-->
      <a-tag v-else color="green"> 按钮 </a-tag>
    </template>
    <template #status="{ text }">
      <a-tag :color="text == 1 ? 'blue' : 'red'">
        {{ text == 1 ? '启用' : '停用' }}
      </a-tag>
    </template>
    <template #action="{ record }">
      <a @click="handleDataForm('编辑', record.id)">编辑</a>
      <a-divider type="vertical" />
      <a-popconfirm
        :title="record.children ? '子节点将一起删除，确定删除?' : '确定删除?'"
        placement="left"
        @confirm="handleDelete('/sys/menu/delete', record.id)"
      >
        <a>删除</a>
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
  import { queryTableMixin } from '../../../mixins/common/query-table-mixin';
  import { defHttp } from '../../../utils/http/axios';

  export default {
    name: 'MainTable',
    components: { DataFormModal },
    mixins: [queryTableMixin],
    data() {
      return {
        url: '/sys/menu/query',
        columns: [
          {
            title: '名称',
            dataIndex: 'name',
            fixed: 'left',
            width: 180,
          },
          {
            title: '地址',
            dataIndex: 'url',
            ellipsis: true,
            width: 120,
          },
          {
            title: '图标',
            dataIndex: 'icon',
            width: 80,
          },
          {
            title: '类型',
            dataIndex: 'type',
            width: 80,
            slots: { customRender: 'type' },
          },
          {
            title: '排序',
            dataIndex: 'sort',
            width: 80,
          },
          {
            title: '授权标识',
            dataIndex: 'permission',
            ellipsis: true,
            width: 160,
          },
          {
            title: '状态',
            dataIndex: 'status',
            width: 80,
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
        this.$refs.dataFormModal.loadMenuData();
        if (id) {
          defHttp.get({ url: '/sys/menu/query/' + id }).then((data) => {
            this.$refs.dataFormModal.show();
            if (data.parentId === 0) {
              data.parentId = undefined;
            }
            this.dataFormModal = { title: title, dataForm: data };
          });
        } else {
          this.$refs.dataFormModal.show();
          this.dataFormModal = { title: title, dataForm: { status: 1, type: 1, sort: 10 } };
        }
      },
    },
  };
</script>
