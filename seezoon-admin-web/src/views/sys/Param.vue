<template>
  <a-form ref="searchForm" :model="searchForm">
    <a-row>
      <a-col :span="4">
        <a-form-item label="参数名" :key="name1" name="name">
          <a-input
            v-model:value="searchForm.name"
            placeholder="参数名"
              :maxlength="50"
          >
          </a-input>
        </a-form-item>
      </a-col>
      <a-col :span="4">
        <a-form-item label="键" :key="paramKey1" name="paramKey">
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
            <a-button type="default" @click="resetSearchForm()">重置 </a-button>
            <a-button type="default" @click="openModal('添加')">添加 </a-button>
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
  >
    <template #status="{ text }">
      <a-tag :color="text == 1 ? 'blue' : 'red'">
        {{ text == 1 ? "有效" : "无效" }}
      </a-tag>
    </template>
    <template #action="{ record }">
      <a @click="openModal('编辑', record.id)">编辑</a>
      <a-divider type="vertical" />

      <a-popconfirm title="确定删除？" @confirm="handleDelete(record.id)">
        <a>删除</a>
      </a-popconfirm>
    </template>
  </a-table>
  <a-modal
    :title="modal.title"
    v-model:visible="modal.visible"
    :confirm-loading="modal.confirmLoading"
    okText="保存"
    :width="750"
    :height="600"
    :maskClosable="false"
    :destroyOnClose="true"
    @ok="handleSave"
  >
    <a-form
      ref="dataForm"
      :model="dataForm"
      :label-col="{ span: 6 }"
      :wrapper-col="{ span: 18 }"
    >
      <a-input v-model:value="dataForm.id" type="hidden"> </a-input>
      <a-row>
        <a-col :span="12">
          <a-form-item
            label="参数名"
            name="name"
            :rules="[
              { required: true, message: '参数名不能为空', whitespace: true },
              { min: 1, max: 50, message: '参数名长度1-50' },
            ]"
          >
            <a-input
              v-model:value="dataForm.name"
              placeholder="参数名"
              :maxlength="50"
            >
            </a-input>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item
            label="唯一键"
            name="paramKey"
            :rules="[
              { required: true, message: '唯一键不能为空', whitespace: true },
              { min: 1, max: 50, message: '唯一键长度1-50' },
              { validator: checkParamKey}
            ]"
          >
            <a-input
              v-model:value="dataForm.paramKey"
              placeholder="唯一键"
              :maxlength="50"
            >
            </a-input>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :span="12">
          <a-form-item
            label="参数值"
            name="paramValue"
            :rules="[
              { required: true, message: '参数值不能为空', whitespace: true },
              { min: 1, max: 50, message: '参数值长度1-50' },
            ]"
          >
            <a-input
              v-model:value="dataForm.paramValue"
              placeholder="参数值"
              :maxlength="50"
            >
            </a-input>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item
            label="状态"
            name="status"
            :rules="[
              {
                required: true,
                type: 'number',
                message: '状态不能为空',
                whitespace: true,
              },
            ]"
          >
            <a-radio-group name="status" v-model:value="dataForm.status">
              <a-radio :value="1"
                ><a-badge status="success" text="有效" />
              </a-radio>
              <a-radio :value="0"
                ><a-badge status="warning" text="无效"
              /></a-radio>
            </a-radio-group>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :span="24">
          <a-form-item
            label="备注"
            name="remarks"
            :label-col="{ span: 3 }"
            :wrapper-col="{ span: 21 }"
          >
            <a-textarea
              v-model:value="dataForm.remarks"
              placeholder="备注"
              :maxlength="255"
              :auto-size="{ minRows: 3, maxRows: 5 }"
            />
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>
<script>
import * as paramService from "@/api/sys/param";
import qs from 'qs' 

export default {
  data() {
    return {
      searchForm: {
        name: "",
        paramKey: "",
      },
      modal: {},
      dataForm: {},
      data: [],
      pagination: {},
      loading: false,
      columns: [
        {
          title: "参数名",
          dataIndex: "name",
        },
        {
          title: "键",
          dataIndex: "paramKey",
        },
        {
          title: "值",
          dataIndex: "paramValue",
        },
        {
          title: "状态",
          dataIndex: "status",
          slots: { customRender: "status" },
        },
        {
          title: "创建时间",
          dataIndex: "createTime",
          sorter: true,
        },
        {
          title: "操作",
          fixd: "right",
          width: 120,
          slots: { customRender: "action" },
        },
      ],
    };
  },
  mounted() {
    this.handleQueryPage();
  },
  methods: {
      checkParamKey(rule, value){
        return new Promise((resolve, reject) => {
         if (!(value && value.trim())) {
             resolve();
             return;
        }
        this.$http.post('/sys/param/checkParamKey',qs.stringify({id:this.dataForm.id,paramKey:value}))
        .then(({data})=>{
            data ? resolve() : reject(`唯一键 ${value} 已存在`)
        });
      });
    },
    getName(text) {
      return text == 1 ? "是" : "否";
    },
    handleTableChange(pagination, filters, sorter) {
      console.log(pagination);
      this.handleQueryPage({
        pageSize: pagination.pageSize,
        page: pagination.current,
        sortField: sorter.field,
        sortOrder: sorter.order,
        ...this.searchForm,
        ...filters,
      });
    },
    handleQueryPage(params = { ...this.searchForm, page: 1, pageSize: 10 }) {
      console.info("pagination")
      this.loading = true;
      paramService.qryPage(params).then(({ code, data: { total, list } }) => {
        this.loading = false;
        this.data = list;
        this.pagination.total = total;
        this.pagination.current = params.page;
      });
    },
    resetSearchForm() {
      this.$refs.searchForm.resetFields();
    },
    openModal(title, id) {
      this.dataForm = {};
      if (id) {
        this.$http.get("/sys/param/query/" + id).then(({ data }) => {
          this.dataForm = data;
        });
      }
      this.modal = { title: title, visible: true };
    },
    handleSave() {
      this.$refs.dataForm
        .validate()
        .then(() => {
          this.modal.confirmLoading = true;
          console.log("values", this.form);
          paramService
            .save(this.dataForm)
            .then(() => {
              this.handleQueryPage();
              this.modal.visible = false;
               this.$message.success('保存成功');
            })
            .finally(() => {
              this.modal.confirmLoading = false;
            });
        })
        .catch((error) => {
          console.log("error", error);
        });
    },
  },
};
</script>