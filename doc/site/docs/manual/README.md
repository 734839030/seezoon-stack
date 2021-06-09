# 前端

前端不是作者专长，前端提供的方案不一定优雅但是够用了，建议在编写时候参考现有例子，这样更好理解。前端原则上只会采用AntdV 的组件，方案大家统一技术栈，Antdv例子全，文档细，本章节只会例举最佳实践相关。其余文档查阅 [Antdv](https://2x.antdv.com/components/overview-cn/) 官网，建议还是简单通读下Vue3和Antdv的文档。
## 数据字典
在登录后`/src/api/sys/index.ts` initAllDict 加载所有字典放入sessionStorage中，业务模块字典可用用于form表单，
或者页面回显数据时候使用，业务模块字典约定在data.ts中。

### 手动编写字典
```typescript
export const templateTypes: Dict[] = [
  {
    label: '基础增删改查',
    value: 0,
  },
  {
    label: '树结构',
    value: 1,
    disabled: true,
  },
];

export const templateTypesMap = dictArray2Map(templateTypes);

```
### 加载DB字典
```typescript
export const xxxDicts: Dict[] = getDict('xxxx')

export const xxxDictsMap = dictArray2Map(xxxDicts);
```
> 默认字典的value都是字符串类型，如果需要转整形，使用getDict('xxxx',true)，表单校验对数类型很敏感。

## 字典使用

渲染select、radio、checkbox等。
- 单选下拉
```html
<a-select
      v-model:value="dataForm.xxx"
      :allowClear="true"
      :options="xxxDicts"
      placeholder="请选择下拉数据"
      style="width: 140px"
    />
```

- 复选框

```html
<a-checkbox-group v-model:value="dataForm.xxxArray" :options="xxxDicts" />
```

- 单选框

```html
<a-radio-group v-model:value="searchForm.status" :options="statusDicts" />
```

- 下拉多选

```html
<a-select
  v-model:value="dataForm.xxxxArray"
  :allowClear="true"
  :options="xxxxDicts"
  mode="multiple"
  placeholder="请选择下拉数据"
  style="width: 140px"
/>
```

数据转换数据回显的时候

```typescript
xxxDictsMap.get('value')
```

## 分页表格

关于数据表格，框架已经封装成mixins，分页和不分页都支持，如果不分页或者需要默认参数，请查看queryTableMixin 中属性，自行覆盖即可。

### 查询表单

代码生成的查询表单为平铺方式，已参数管理为例：

```vue
<a-form
    ref="searchForm"
    :labelCol="this.labelCol"
    :model="searchForm"
    :wrapperCol="this.wrapperCol"
    layout="inline"
  >
    <a-form-item label="参数名" name="name">
      <a-input v-model:value="searchForm.name" :maxlength="50" placeholder="请输入参数名" />
    </a-form-item>
    <a-form-item label="键" name="paramKey">
      <a-input v-model:value="searchForm.paramKey" :maxlength="50" placeholder="请输入唯一键" />
    </a-form-item>
    <a-form-item>
      <a-space>
        <a-button v-auth="'sys:param:query'" type="primary" @click="handleQuery()">查询</a-button>
        <a-button type="default" @click="this.$refs.searchForm.resetFields()">重置</a-button>
        <a-button
          v-auth="'sys:param:save'"
          type="default"
          @click="this.$refs.dataFormModal.open('添加')"
          >添加
        </a-button>
      </a-space>
    </a-form-item>
  </a-form>
```

如果条件较多，请使用Antdv 提供的栅格布局，将`layout="inline"`去掉，把a-form-item放入栅格中，栅格划分多少分自行决定，和为24。

```vue
 <a-row>
   <a-col :span="4"></a-col>
   <a-col :span="4"></a-col>
   <a-col :span="4"></a-col>
   <a-col :span="4"></a-col>
   <a-col :span="4"></a-col>
   <a-col :span="4"></a-col>
 </a-row>  
```

如果有多行默认行间隔比较宽，可以根据情况添加自定义样式：

```vue
<a-row style="margin-top: -20px">
</a-row>  
```

如果最后一个元素离按钮较近，通常select的内容过长会到时和最后的查询按钮挨的较近，这种情况则使用：

```vue
<a-col :span="4" class="mr-5">
</a-col>
```

### 表格

通过mixins方式，只需要编写如下代码即可，后台分页结构数据按框架现有的即可。如果离上面元素太近可以使用windcss 默认的样式`mt-4`,代表`margin-top 4`个标准单位。

> 如果表格字段较多，可以使用横向滚动在a-table`:scroll="{ x: 1500 }"`，宽度自行定义，然后对 column 添加width:属性,综合不要超过这个即可以。根据经验，不要全部column设置宽度。

```vue
<a-table
    :columns="columns"
    :data-source="data"
    :loading="loading"
    :pagination="pagination"
    :row-key="(record) => record.id"
    bordered
    class="mt-4 pr-4"
    size="small"
    @change="handleTableChange"
  >
    <template #status="{ text }">
      <a-tag :color="text == 1 ? 'blue' : 'red'">
        {{ text == 1 ? '有效' : '无效' }}
      </a-tag>
    </template>
    <template #action="{ record }">
      <a v-auth="'sys:param:update'" @click="this.$refs.dataFormModal.open('编辑', record.id)"
        >编辑</a
      >
      <a-divider type="vertical" />
      <a-popconfirm
        placement="left"
        title="确定删除？"
        @confirm="handleDelete('/sys/param/delete', record.id)"
      >
        <a v-auth="'sys:param:delete'">删除</a>
      </a-popconfirm>
    </template>
  </a-table>
```

组件中加入如下代码即可以。

```typescript
mixins: [queryTableMixin],
data() {
      return {
        url: '/sys/param/query',
        columns: [
          {
            title: '参数名',
            dataIndex: 'name',
          },
          {
            title: '键',
            dataIndex: 'paramKey',
          },
          {
            title: '值',
            dataIndex: 'paramValue',
          },
          {
            title: '状态',
            dataIndex: 'status',
            slots: { customRender: 'status' },
          },
          {
            title: '创建时间',
            dataIndex: 'createTime',
          },
          {
            title: '修改时间',
            dataIndex: 'updateTime',
            sorter: true,
          },
          {
            title: '操作',
            fixed: 'right',
            width: 120,
            slots: { customRender: 'action' },
          },
        ],
      };
    },
    mounted() {
      this.handleQuery();
    },
```

## 数据编辑

类似的使用mixins ,封装`dataFormModalMixin` ,将新增、修改、参数验证，回调等统一封装。

```vue
<template>
  <a-modal
    v-model:visible="visible"
    :confirm-loading="confirmLoading"
    :destroyOnClose="true"
    :height="this.height"
    :maskClosable="false"
    :title="title"
    :width="this.width"
    okText="保存"
    @ok="handleOk(this.dataForm.id === undefined ? '/sys/param/save' : '/sys/param/update')"
  >
    <a-form
      ref="dataForm"
      :label-col="this.labelCol"
      :model="dataForm"
      :wrapper-col="this.wrapperCol"
    >
      <a-input v-model:value="dataForm.id" type="hidden" />
      <a-row>
        <a-col :md="12" :xs="24">
          <a-form-item
            :rules="[
              { required: true, message: '参数名不能为空', whitespace: true },
              { min: 1, max: 50, message: '参数名长度1-50' },
            ]"
            label="参数名"
            name="name"
          >
            <a-input v-model:value="dataForm.name" :maxlength="50" placeholder="参数名" />
          </a-form-item>
        </a-col>
        <a-col :md="12" :xs="24">
          <a-form-item
            :rules="[
              { required: true, message: '唯一键不能为空', whitespace: true },
              { min: 1, max: 50, message: '唯一键长度1-50' },
              { validator: checkParamKey, trigger: 'blur' },
            ]"
            label="唯一键"
            name="paramKey"
          >
            <a-input v-model:value="dataForm.paramKey" :maxlength="50" placeholder="唯一键" />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :md="12" :xs="24">
          <a-form-item
            :rules="[
              { required: true, message: '参数值不能为空', whitespace: true },
              { min: 1, max: 100, message: '参数值长度1-100' },
            ]"
            label="参数值"
            name="paramValue"
          >
            <a-input v-model:value="dataForm.paramValue" :maxlength="100" placeholder="参数值" />
          </a-form-item>
        </a-col>
        <a-col :md="12" :xs="24">
          <a-form-item
            :rules="[
              {
                required: true,
                type: 'number',
                message: '状态不能为空',
                whitespace: true,
              },
            ]"
            label="状态"
            name="status"
          >
            <a-radio-group v-model:value="dataForm.status" name="status">
              <a-radio :value="1">
                <a-badge status="success" text="有效" />
              </a-radio>
              <a-radio :value="0">
                <a-badge status="warning" text="无效" />
              </a-radio>
            </a-radio-group>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :span="24">
          <a-form-item
            :label-col="{ span: 3 }"
            :wrapper-col="{ span: 21 }"
            label="备注"
            name="remarks"
          >
            <a-textarea
              v-model:value="dataForm.remarks"
              :auto-size="{ minRows: 3, maxRows: 5 }"
              :maxlength="255"
              placeholder="备注"
            />
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
  import { dataFormModalMixin } from '../../../mixins/common/data-form-mixin-modal.js';
  import { defHttp } from '../../../utils/http/axios';

  export default {
    name: 'DataFormModal',
    mixins: [dataFormModalMixin],
    emits: ['refreshQuery'],
    methods: {
      checkParamKey(rule, value) {
        return this.uniqueFieldSimpleValidation(
          '/sys/param/check_param_key',
          value,
          {
            id: this.dataForm.id,
            paramKey: value,
          },
          `唯一键 ${value} 已存在`
        );
      },
      open(title, id) {
        this.visible = true;
        this.title = title;
        if (null != id) {
          defHttp.get({ url: '/sys/param/query/' + id }).then((data) => {
            this.dataForm = data;
          });
        } else {
          this.dataForm = { status: 1 };
        }
      },
      // 保存后回调
      handleOkCb() {
        this.$emit('refreshQuery');
      },
    },
  };
</script>

```

## 数据查看

利用`Antdv descriptions`，响应式组件，查看数据很方便，利用数据字典组件，回显无烦恼。

```vue
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
      const open = function (id) {
        visible.value = true;
        defHttp.get({ url: '/sys/demo/query/' + id }).then((_data) => {
          data.value = _data;
        });
      };
      return {
        visible,
        open,
        data,
        inputSelectDictsMap,
        inputRadioDictsMap,
      };
    },
  };
</script>

```

## 图片&文件

这里自行封装SUploader组件，在数据查看和数据编辑场景都可以使用，重要参数如下：

> 组件使用的是相对地址

| 参数名   | 是否必须 | 说明                                                         |
| -------- | -------- | ------------------------------------------------------------ |
| value    | √        | 绑定的相对路径                                               |
| disabled |          | 是否禁用，通常查看时候是true                                 |
| listType |          | 展示类型:text(文件名), picture(图片和文件名) 和 picture-card(照片墙)，默认是text |
| limit    |          | 上传数量，默认1                                              |
| multiple |          | 是否允许多文件，默认false.注意和limit搭配。                  |
| accept   |          | 允许类型，和原生input type='file'  用法一致，如image/*       |

数据编辑中例子：

```vue
<s-uploader v-model:value="dataForm.image" accept="image/*" listType="picture-card" />
```

在数据查看中的例子：

```vue
<s-uploader v-model:value="data.image" :disabled="true" listType="picture-card" />
```

## 全局配置

涉及到几个可能变更配置文件，一看懂。

```
.env.development
.env.production
src/settings/projectSetting.ts
src/settings/localeSetting.ts
```

