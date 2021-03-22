<template>
  <a-modal
    v-model:visible="visible"
    :destroyOnClose="true"
    :height="600"
    title="查看"
    :width="750"
    :footer="null"
  >
    <a-descriptions bordered size="small">
      <a-descriptions-item label="文本">{{ data.inputText }}</a-descriptions-item>
      <a-descriptions-item label="下拉">
        {{ inputSelectDictsMap.get(data.inputSelect) }}
      </a-descriptions-item>
      <a-descriptions-item label="单选">
        {{ inputRadioDictsMap.get(data.inputRadio) }}
      </a-descriptions-item>
      <a-descriptions-item label="多选"> {{ data.inputCheckbox }} </a-descriptions-item>
      <a-descriptions-item label="文本域"> {{ data.inputTextarea }} </a-descriptions-item>
      <a-descriptions-item label="日期"> {{ data.inputDate }} </a-descriptions-item>
      <a-descriptions-item label="整数"> {{ data.inputZhengshu }} </a-descriptions-item>
      <a-descriptions-item label="小数"> {{ data.inputXiaoshu }} </a-descriptions-item>
      <a-descriptions-item label="图片">
        <s-uploader v-model:value="data.image" :disabled="true" listType="picture-card" />
      </a-descriptions-item>
      <a-descriptions-item label="文件">
        <s-uploader v-model:value="data.file" :disabled="true" />
      </a-descriptions-item>
      <a-descriptions-item label="富文本"><p v-html="data.richText"></p> </a-descriptions-item>
      <a-descriptions-item label="备注"> {{ data.remarks }} </a-descriptions-item>
    </a-descriptions>
  </a-modal>
</template>

<script>
  import { ref } from 'vue';
  import { defHttp } from '../../../utils/http/axios';
  import { inputRadioDictsMap, inputSelectDictsMap } from './data';
  import SUploader from '../../../components/SUploader/index.vue';

  export default {
    name: 'DataViewModal',
    components: { SUploader },
    setup() {
      const visible = ref(false);
      const data = ref({});
      const show = function (id) {
        visible.value = true;
        defHttp.get({ url: '/sys/demo/query/' + id }).then((_data) => {
          data.value = _data;
        });
      };
      return {
        visible,
        show,
        data,
        inputSelectDictsMap,
        inputRadioDictsMap,
      };
    },
  };
</script>
