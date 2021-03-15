import { defHttp } from '/@/utils/http/axios';
// 用于子组件的时候,prop 是只读的，modal 自带的关闭方法无法扩展调用$emit('update:visible',false);
export const dataFormModalMixin = {
  props: {
    title: String,
    dataForm: Object,
  },
  data() {
    return {
      width: 750,
      height: 600,
      labelCol: { span: 6 },
      wrapperCol: { span: 18 },
      visible: false,
      confirmLoading: false,
    };
  },
  methods: {
    show() {
      this.visible = true;
    },
    handleOkCb() {
      // 需要覆写
    },
    handleOk(url) {
      this.$refs.dataForm
        .validate()
        .then(() => {
          this.confirmLoading = true;
          defHttp
            .post({ url: url, params: this.dataForm })
            .then(() => {
              this.handleOkCb();
              this.visible = false;
              this.$message.success('操作成功');
            })
            .catch((error) => {
              console.error(error);
            })
            .finally(() => {
              this.confirmLoading = false;
            });
        })
        .catch((error) => {
          console.log('error', error);
        });
    },
    // 唯一约束快速校验
    uniqueFieldSimpleValidation(url, value, params, message) {
      return new Promise((resolve, reject) => {
        if (!(value && value.trim())) {
          resolve();
          return;
        }
        defHttp.postForm(url, params).then((data) => {
          data ? resolve() : reject(message ? message : `${value} 已存在`);
        });
      });
    },
  },
};
