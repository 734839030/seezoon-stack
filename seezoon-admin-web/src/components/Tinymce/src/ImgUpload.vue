<template>
  <div :class="prefixCls">
    <Upload
      name="file"
      multiple
      :customRequest="customRequest"
      action="/sys/file/upload"
      :showUploadList="false"
      accept=".jpg,.jpeg,.gif,.png,.webp"
    >
      <a-button type="primary" :loading="uploadBtnLoading">
        {{ t('component.upload.imgUpload') }}
      </a-button>
    </Upload>
  </div>
</template>
<script lang="ts">
  import { defineComponent, ref } from 'vue';

  import { Upload } from 'ant-design-vue';
  import { message } from 'ant-design-vue';

  import { useDesign } from '/@/hooks/web/useDesign';
  //import { useGlobSetting } from '/@/hooks/setting';
  import { useI18n } from '/@/hooks/web/useI18n';
  import { defHttp } from '/@/utils/http/axios';

  export default defineComponent({
    name: 'TinymceImageUpload',
    components: { Upload },
    emits: ['uploading', 'done', 'error'],
    setup(_, { emit }) {
      const uploadBtnLoading = ref(false);
      const { t } = useI18n();
      const { prefixCls } = useDesign('tinymce-img-upload');
      function customRequest(formData) {
        const form = new FormData();
        form.append(formData.filename, formData.file);
        uploadBtnLoading.value = true;
        emit('uploading', formData.file.name);
        defHttp
          .postFile(formData.action, form)
          .then((data) => {
            emit('done', data.name, data.url);
          })
          .catch(function (e) {
            message.error('上传失败:' + e);
          })
          .finally(() => (uploadBtnLoading.value = false));
      }
      return {
        prefixCls,
        uploadBtnLoading,
        customRequest,
        t,
      };
    },
  });
</script>
<style lang="less" scoped>
  @prefix-cls: ~'@{namespace}-tinymce-img-upload';

  .@{prefix-cls} {
    position: absolute;
    top: 4px;
    right: 10px;
    z-index: 20;
  }
</style>
