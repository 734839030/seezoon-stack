import request from '@/utils/request.js'
// 通过混入使用，必须覆盖url，columns属性，data中key已外部组件优先，同名会全被外部对象覆盖
// page的属性
export const pageTableMixin = {
    data() {
        return {
            url: '', // 请求地址
            columns: [], // 表格头部
            searchForm: {}, // 搜索条件
            data: [],  // 表格数据
            pagination: {
                showSizeChanger: true,
                //showQuickJumper:true,
                pageSize: 10,
                pageSizeOptions: ['10', '20', '50', '100'],
                //showTotal:(total, range) => `${range[0]}-${range[1]} 共 ${total} 条`
                showTotal: (total) => `共 ${total} 条`
            },
            loading: false
        }
    },
    methods: {
        handleQueryPage(params = {...this.searchForm, page: 1, pageSize: this.pagination.pageSize}) {
            this.loading = true
            request.post(this.url, params).then(({data: {total, list}}) => {
                this.loading = false
                this.data = list
                this.pagination.total = total
                this.pagination.current = params.page
            }).catch((error) => {
                this.loading = false
                console.info(error)
            });
        },
        handleTableChange(pagination, filters, sorter) {
            this.loading = true
            this.handleQueryPage({
                pageSize: pagination.pageSize,
                page: pagination.current,
                sortField: sorter.field,
                sortOrder: sorter.order,
                ...this.searchForm,
                ...filters
            })
        },
        // 选择性覆写
        handleDeleteCb() {

        },
        handleDelete(url, id) {
            this.$http.post(url, `id=${id}`).then(() => {
                this.$message.success('删除成功')
                this.handleQueryPage()
                this.handleDeleteCb(id);
            });
        },
    }
}
