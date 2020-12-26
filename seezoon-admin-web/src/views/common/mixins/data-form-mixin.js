import request from '@/utils/request.js'
// 用于子组件的时候,prop 是只读的，所以visible 不能在prop
export const dataFormMixin = {
    props: {
        title: String,
        dataForm: Object
    },
    data() {
        return {
            visible: false,
            confirmLoading: false
        }
    },
    methods: {
        // visible 不能在prop
        showModal() {
            this.visible = true
        },
        handleOkCb() {
            // 需要覆写
        },
        handleOk(url) {
            this.$refs.dataForm
                .validate()
                .then(() => {
                    this.confirmLoading = true;
                    request.post(url, this.dataForm)
                        .then(() => {
                            this.handleOkCb()
                            this.visible = false;
                            this.$message.success('操作成功')
                        }).catch((error) => {
                        console.error(error)
                    }).finally(() => {
                        this.confirmLoading = false
                    });
                }).catch((error) => {
                console.log("error", error)
            });
        }
    }
}