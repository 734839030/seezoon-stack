<template>
  <a-upload
    v-model:fileList="fileList"
    :accept="accept"
    :customRequest="customRequest"
    :list-type="listType"
    :multiple="multiple"
    action="/sys/file/upload"
    name="file"
    :disabled="disabled"
    @change="handleChange"
  >
    <div v-if="fileList.length < limit && !disabled">
      <a-button :loading="uploadBtnLoading" type="dashed">
        <cloud-upload-outlined />
      </a-button>
    </div>
  </a-upload>
</template>
<script>
  import { CloudUploadOutlined } from '@ant-design/icons-vue';
  import { defHttp } from '../../utils/http/axios';

  export default {
    name: 'SUploader',
    components: { CloudUploadOutlined },
    props: {
      // text, picture 和 picture-card
      listType: {
        type: String,
        default: 'text',
      },
      limit: {
        type: Number,
        default: 1,
      },
      accept: {
        type: String,
      },
      multiple: {
        type: Boolean,
        default: false,
      },
      // 相对路径多个逗号分隔
      value: {
        type: String,
      },
      disabled: {
        type: Boolean,
        default: false,
      },
    },
    emits: ['update:value', 'change'],
    data() {
      return {
        uploadBtnLoading: false,
        fileList: [],
      };
    },
    watch: {
      value: {
        immediate: true, //可以首次设置进到handler
        handler: function (val) {
          // 不能写成=> 这种函数，会导致this undefined
          // 首次回显
          if (val && this.fileList.length == 0) {
            defHttp
              .postForm('/sys/file/info', { relativePaths: val, includeFileName: true })
              .then((data) => {
                this.fileList = data;
              });
          }
        },
      },
    },
    methods: {
      // 上传和删除会触发变化
      handleChange({ file, fileList }) {
        if (file.status === 'uploading') {
          // 去除自带的上传数据
          this.fileList = fileList.slice(0, -1);
        }
        let paths = [];
        this.fileList.forEach((val) => {
          paths.push(val.relativePath);
        });
        const value = paths.join(',');
        this.$emit('update:value', value);
        this.$emit('change', value);
      },
      customRequest(formData) {
        const form = new FormData();
        form.append(formData.filename, formData.file);
        this.uploadBtnLoading = true;
        defHttp
          .postFile(formData.action, form)
          .then((data) => {
            this.fileList.push(data);
            this.handleChange({ file: data, fileList: this.fileList });
          })
          .finally(() => (this.uploadBtnLoading = false));
      },
    },
  };
</script>
