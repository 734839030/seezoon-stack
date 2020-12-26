<template>
  <!-- 查询表单 -->
  <a-form ref="searchForm" :model="searchForm">
    <a-row>
      <a-col :span="4">
        <a-form-item label="参数名" name="name">
          <a-input
            v-model:value="searchForm.name"
            placeholder="参数名"
            :maxlength="50"
          >
          </a-input>
        </a-form-item>
      </a-col>
      <a-col :span="4">
        <a-form-item label="键" name="paramKey">
          <a-input
            v-model:value="searchForm.paramKey"
            placeholder="唯一键"
            :maxlength="50"
          >
          </a-input>
        </a-form-item>
      </a-col>
      <a-col :span="3">
        <a-form-item>
          <a-space>
            <a-button type="primary" @click="handleQueryPage()">查询</a-button>
            <a-button type="default" @click="this.$refs.searchForm.resetFields();">重置 </a-button>
            <a-button type="default" @click="handleDataForm('添加')">添加 </a-button>
          </a-space>
        </a-form-item>
      </a-col>
    </a-row>
  </a-form>
  <a-table
    :columns="columns"
    :row-key="(record) => record.id"
    :data-source="data"
    :pagination="pagination"
    :loading="loading"
    @change="handleTableChange"
    bordered
    :scroll="{y: 600 }"
  >
    <template #status="{ text }">
      <a-tag :color="text == 1 ? 'blue' : 'red'">
        {{ text == 1 ? "有效" : "无效" }}
      </a-tag>
    </template>
    <template #action="{ record }">
      <a @click="handleDataForm('编辑', record.id)">编辑</a>
      <a-divider type="vertical" />
      <a-popconfirm title="确定删除？" @confirm="handleDelete('/sys/param/delete',record.id)">
        <a>删除</a>
      </a-popconfirm>
    </template>
  </a-table>
  <data-form ref="dataForm" :title="dataForm.title" :data-form="dataForm.data" @refreshQueryPage="handleQueryPage"></data-form>
</template>
<script>
import { pageTableMixin }  from "@/views/common/mixins/page-table-mixin";
import DataForm from './DataForm';

export default {
  name:'MainTable',
  components:{DataForm},
  mixins:[pageTableMixin],
  data() {
    return {
        url:'/sys/param/query',
        columns: [
        {
          title: '参数名',
          dataIndex: 'name',
        },
        {
          title: '键',
          dataIndex: 'paramKey',
        },
        {
          title: '值',
          dataIndex: 'paramValue',
        },
        {
          title: '状态',
          dataIndex: 'status',
          slots: { customRender: 'status' },
        },
        {
          title: '创建时间',
          dataIndex: 'createTime',
          sorter: true,
        },
        {
          title: '操作',
          fixd: 'right',
          width: 120,
          slots: { customRender: 'action' },
        },
      ],
      dataForm:{
        
      }
    } 
  },
  mounted() {
    this.handleQueryPage();
  },
  methods:{
    handleDataForm(title,id){
        if (id) {
          this.$http.get('/sys/param/query/' + id).then(({ data }) => {
             this.$refs.dataForm.showModal();
             this.dataForm = {title: title, data: data};
          });
        } else {
          this.$refs.dataForm.showModal();
          this.dataForm = { title: title, data:{}};
        }
        
    }
  }
};
</script>