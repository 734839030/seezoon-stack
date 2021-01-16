<template>
  <!-- 查询表单 -->
  <a-form ref="searchForm" :model="searchForm">
    <a-row :gutter="10">
      <a-col :md="4" :xs="24">
        <a-form-item :wrapperCol="{span:18}" label="类型" name="type">
          <a-select v-model:value="searchForm.type" :allowClear="true" :options="dictTypes" placeholder="请选择类型"
                    show-search>
          </a-select>
        </a-form-item>
      </a-col>
      <a-col :md="4" :xs="24">
        <a-form-item label="编码" name="code">
          <a-input v-model:value="searchForm.code" :maxlength="50" placeholder="请输入编码"></a-input>
        </a-form-item>
      </a-col>
      <a-col :md="4" :xs="24">
        <a-form-item label="名称" name="name">
          <a-input v-model:value="searchForm.name" :maxlength="50" placeholder="请输入名称"></a-input>
        </a-form-item>
      </a-col>
      <a-col :md="4" :xs="24">
        <a-form-item>
          <a-space>
            <a-button type="primary" @click="handleQueryPage()">查询</a-button>
            <a-button type="default" @click="this.$refs.searchForm.resetFields()">重置</a-button>
            <a-button type="default" @click="handleDataForm('添加')">添加</a-button>
          </a-space>
        </a-form-item>
      </a-col>
    </a-row>
  </a-form>
  <a-table
      :columns="columns"
      :data-source="data"
      :loading="loading"
      :pagination="pagination"
      :row-key="(record) => record.id"
      :scroll="{y: 600 }"
      bordered
      size="small"
      @change="handleTableChange">
    <template #status="{ text }">
      <a-tag :color="text == 1 ? 'blue' : 'red'">
        {{ text == 1 ? "有效" : "无效" }}
      </a-tag>
    </template>
    <template #action="{ record }">
      <a @click="handleDataForm('编辑', record.id)">编辑</a>
      <a-divider type="vertical"/>
      <a-popconfirm placement="left" title="确定删除？" @confirm="handleDelete('/sys/dict/delete',record.id)">
        <a>删除</a>
      </a-popconfirm>
    </template>
  </a-table>
  <data-form-modal ref="dataFormModal" :data-form="dataFormModal.dataForm" :title="dataFormModal.title"
                   @refreshQueryPage="handleQueryPage"></data-form-modal>
</template>
<script>
import {pageTableMixin} from "@/mixins/common/page-table-mixin";
import DataFormModal from './DataFormModal';
import {onMounted, ref} from 'vue'
import {getTypes} from "@/api/sys";

export default {
  name: 'MainTable',
  components: {DataFormModal},
  mixins: [pageTableMixin],
  setup(props) {
    let dictTypes = ref([])
    onMounted(async () => {
      dictTypes.value = await getTypes()
    })
    return {
      dictTypes
    }
  },
  data() {
    return {
      url: '/sys/dict/query',
      columns: [
        {
          title: '类型',
          dataIndex: 'type',
        },
        {
          title: '编码',
          dataIndex: 'code',
        },
        {
          title: '名称',
          dataIndex: 'name',
        },
        {
          title: '排序',
          dataIndex: 'sort',
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
        this.$http.get('/sys/dict/query/' + id).then(({data}) => {
          this.$refs.dataFormModal.show();
          this.dataFormModal = {title: title, dataForm: data};
        });
      } else {
        this.$refs.dataFormModal.show();
        this.dataFormModal = {title: title, dataForm: {sort: 10, type: this.searchForm.type}};
      }
    }
  }
};
</script>