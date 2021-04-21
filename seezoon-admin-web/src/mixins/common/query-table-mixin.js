// 通过混入使用，必须覆盖url，columns属性，data中key已外部组件优先，同名会全被外部对象覆盖
// page的属性 可以分宜也可以不分页，由a-table 中参数决定
import { defHttp } from '../../utils/http/axios';

export const queryTableMixin = {
  data() {
    return {
      url: '', // 请求地址
      columns: [], // 表格头部
      searchForm: {}, // 搜索条件
      labelCol: { span: 8 },
      wrapperCol: { span: 16 },
      scrollX: 'max-content',
      scrollY: 'max-content',
      data: [], // 表格数据
      pagination: {
        showSizeChanger: true,
        //showQuickJumper:true,
        pageSize: 10,
        pageSizeOptions: ['10', '20', '50', '100'],
        //showTotal:(total, range) => `${range[0]}-${range[1]} 共 ${total} 条`
        showTotal: (total) => `共 ${total} 条`,
      },
      loading: false,
    };
  },
  methods: {
    handleQuery(params = { ...this.searchForm, page: 1, pageSize: this.pagination.pageSize }) {
      this.loading = true;
      // 本可以函数参数多重解构,但分分页场景没有total和list  ({data: {total, list}}
      defHttp
        .post({ url: this.url, params: params })
        .then((data) => {
          this.loading = false;
          // 兼容不分页的
          if (data instanceof Array) {
            this.data = data;
            return;
          }
          let { total, list } = data;
          this.data = list;
          this.pagination.total = total;
          this.pagination.current = params.page;
        })
        .catch((error) => {
          this.loading = false;
          console.info(error);
        });
    },
    handleTableChange(pagination, filters, sorter) {
      this.loading = true;
      this.handleQuery({
        pageSize: pagination.pageSize,
        page: pagination.current,
        sortField: sorter.field,
        sortOrder: sorter.order,
        ...this.searchForm,
        ...filters,
      });
    },
    // 选择性覆写_u 可以lint 不告警
    handleDeleteCb(_u) {},
    handleDelete(url, id) {
      defHttp.postForm(url, { id: id }).then(() => {
        this.$message.success('删除成功');
        this.handleQuery();
        this.handleDeleteCb(id);
      });
    },
  },
};
