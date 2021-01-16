<template>
  <a-space direction="vertical">
    <!-- 查询表单 -->
    <a-form ref="searchForm" :model="searchForm" layout="inline">
      <a-form-item label="文件名" name="name">
        <a-input
            v-model:value="searchForm.name" :maxlength="200" placeholder="模糊搜索">
        </a-input>
      </a-form-item>
      <a-form-item label="相对路径" name="relativePath">
        <a-input
            v-model:value="searchForm.relativePath" :maxlength="100" placeholder="相对路径">
        </a-input>
      </a-form-item>
      <a-form-item label="上传日期" name="createDateRange">
        <a-range-picker v-model:value="searchForm.createDateRange">
        </a-range-picker>
      </a-form-item>
      <a-form-item>
        <a-space>
          <a-button type="primary" @click="handleQueryPage()">查询</a-button>
          <a-button type="default" @click="this.$refs.searchForm.resetFields();">重置</a-button>
          <a-upload
              :customRequest="customRequest"
              :multiple="true"
              :showUploadList="false"
              action="/sys/file/upload"
              name="file">
            <a-button :loading="uploadBtnLoading" type="default">
              <CloudUploadOutlined/>
            </a-button>
          </a-upload>
        </a-space>
      </a-form-item>
    </a-form>
    <a-table :columns="columns" :data-source="data" :loading="loading" :pagination="pagination"
             :row-key="(record) => record.id" :scroll="{y: 600 }" bordered size="small" @change="handleTableChange">
      <template #action="{ record }">
        <a @click="preview(record.url)">预览</a>
        <a-divider type="vertical"/>
        <a @click="download(record.id)">下载</a>
        <a-divider type="vertical"/>
        <a-popconfirm placement="left" title="确定删除？" @confirm="handleDelete('/sys/file/delete',record.id)">
          <a>删除</a>
        </a-popconfirm>
      </template>
    </a-table>
  </a-space>
</template>
<script>
import {pageTableMixin} from "@/mixins/common/page-table-mixin";
import {CloudUploadOutlined} from '@ant-design/icons-vue';
import moment from 'moment'

export default {
  name: 'MainTable',
  components: {CloudUploadOutlined},
  mixins: [pageTableMixin],
  data() {
    return {
      searchForm: {
        createDateRange: [moment().subtract(7, 'days').format('YYYY-MM-DD'), moment().format('YYYY-MM-DD')]
      },
      url: '/sys/file/query',
      columns: [
        {
          title: '文件名',
          dataIndex: 'name',
        },
        {
          title: '文件类型',
          dataIndex: 'contentType',
        },
        {
          title: '文件大小(B)',
          dataIndex: 'fileSize',
        },
        {
          title: '上传时间',
          dataIndex: 'createTime',
          sorter: true,
        },
        {
          title: '操作',
          fixed: 'right',
          width: 140,
          slots: {customRender: 'action'},
        },
      ],
      uploadBtnLoading: false
    }
  },
  mounted() {
    this.handleQueryPage();
  },
  methods: {
    preview(url) {
      window.open(url, '_blank')
    },
    download(id) {
      window.open(this.$http.defaults.baseURL + '/sys/file/download?id=' + id, '_blank')
    },
    customRequest(formData) {
      const form = new FormData();
      form.append(formData.filename, formData.file);
      this.uploadBtnLoading = true;
      this.$http.post(formData.action, form).then(({data}) => {
        this.$message.success(`${data.originalFilename} 上传成功`);
        this.handleQueryPage();
      }).finally(() => this.uploadBtnLoading = false);
    }
  }
};
</script>