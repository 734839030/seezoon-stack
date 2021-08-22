<template>
  <!-- 查询表单 -->
  <a-form
    ref="searchForm"
    :labelCol="labelCol"
    :model="searchForm"
    :wrapperCol="wrapperCol"
    layout="inline"
  >
    <a-form-item label="文件名" name="name">
      <a-input v-model:value="searchForm.name" :maxlength="200" placeholder="模糊搜索" />
    </a-form-item>
    <a-form-item label="相对路径" name="relativePath">
      <a-input v-model:value="searchForm.relativePath" :maxlength="100" placeholder="相对路径" />
    </a-form-item>
    <a-form-item label="上传日期" name="createDateRange">
      <a-range-picker
        v-model:value="searchForm.createDateRange"
        :allowClear="false"
        valueFormat="YYYY-MM-DD"
      />
    </a-form-item>
    <a-form-item>
      <a-space>
        <a-button v-auth="'sys:file:query'" type="primary" @click="handleQuery()">查询</a-button>
        <!--<a-button type="default" @click="$refs.searchForm.resetFields()">重置</a-button>-->
        <a-upload
          :customRequest="customRequest"
          :multiple="true"
          :showUploadList="false"
          action="/sys/file/upload"
          name="file"
        >
          <a-button v-auth="'sys:file:upload'" :loading="uploadBtnLoading" type="default">
            <cloud-upload-outlined />
            上传
          </a-button>
        </a-upload>
        <a-button
          v-auth="'sys:file:delete'"
          :disabled="selectedRowKeys.length == 0"
          type="default"
          @click="batchDelete(selectedRowKeys)"
          >删除
        </a-button>
      </a-space>
    </a-form-item>
  </a-form>
  <a-table
    :columns="columns"
    :data-source="data"
    :loading="loading"
    :pagination="pagination"
    :row-key="(record) => record.id"
    :row-selection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }"
    bordered
    class="mt-4"
    size="small"
    @change="handleTableChange"
  >
    <template #action="{ record }">
      <a v-auth="'sys:file:query'" @click="preview(record.url)">预览</a>
      <a-divider type="vertical" />
      <a v-auth="'sys:file:download'" @click="download(record.id)">下载</a>
      <a-divider type="vertical" />
      <a-popconfirm
        placement="left"
        title="确定删除？"
        @confirm="handleDelete('/sys/file/delete', record.id)"
      >
        <a v-auth="'sys:file:delete'">删除</a>
      </a-popconfirm>
    </template>
  </a-table>
</template>
<script>
  import { CloudUploadOutlined } from '@ant-design/icons-vue';
  import moment from 'moment';
  import { queryTableMixin } from '../../../mixins/common/query-table-mixin';
  import { defHttp } from '../../../utils/http/axios';
  import { openWindow } from '../../../utils';
  import { useGlobSetting } from '../../../hooks/setting';

  export default {
    name: 'MainTable',
    components: { CloudUploadOutlined },
    mixins: [queryTableMixin],
    data() {
      return {
        searchForm: {
          createDateRange: [moment().day(-7).format('YYYY-MM-DD'), moment().format('YYYY-MM-DD')],
        },
        url: '/sys/file/query',
        selectedRowKeys: [],
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
            slots: { customRender: 'action' },
          },
        ],
        uploadBtnLoading: false,
      };
    },
    mounted() {
      this.handleQuery();
    },
    methods: {
      onSelectChange(selectedRowKeys) {
        this.selectedRowKeys = selectedRowKeys;
      },
      preview(url) {
        openWindow(url);
      },
      download(id) {
        const { apiUrl } = useGlobSetting();
        openWindow(apiUrl + '/sys/file/download?id=' + id);
      },
      customRequest(formData) {
        const form = new FormData();
        form.append(formData.filename, formData.file);
        this.uploadBtnLoading = true;
        defHttp
          .postFile(formData.action, form)
          .then((data) => {
            this.$message.success(`${data.name} 上传成功`);
            this.handleQuery();
          })
          .finally(() => (this.uploadBtnLoading = false));
      },
      batchDelete(selectedRowKeys) {
        defHttp.postForm('/sys/file/delete_batch', { ids: selectedRowKeys.join(',') }).then(() => {
          this.$message.success('删除成功');
          this.selectedRowKeys = [];
          this.handleQuery();
        });
      },
    },
  };
</script>
