<template>
  <a-drawer
    v-model:visible="visible"
    :destroyOnClose="true"
    :height="'80%'"
    :maskClosable="false"
    :title="title"
    :width="'80%'"
  >
    <a-form
      ref="dataForm"
      :label-col="this.labelCol"
      :model="dataForm"
      :wrapper-col="this.wrapperCol"
    >
      <a-input v-model:value="dataForm.id" type="hidden" />
      <a-row>
        <a-col :md="8">
          <a-form-item
            :rules="[{ required: true, message: '表不能为空', whitespace: true }]"
            label="表名"
            name="tableName"
          >
            <a-input v-model:value="dataForm.tableName" :disabled="true" />
          </a-form-item>
        </a-col>
        <a-col :md="8">
          <a-form-item
            :rules="[{ required: true, message: '菜单名不能为空', whitespace: true }]"
            label="菜单名"
            name="menuName"
          >
            <a-input v-model:value="dataForm.menuName" :maxlength="50" />
          </a-form-item>
        </a-col>
        <a-col :md="8">
          <a-form-item
            :rules="[{ required: true, message: '模块名不能为空', whitespace: true }]"
            label="模块名"
            name="moduleName"
          >
            <a-input
              v-model:value="dataForm.moduleName"
              :maxlength="10"
              placeholder="英文名如sys"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :md="8">
          <a-form-item
            :rules="[{ required: true, message: '功能名不能为空', whitespace: true }]"
            label="功能名"
            name="functionName"
          >
            <a-input
              v-model:value="dataForm.functionName"
              :maxlength="20"
              placeholder="英文名如demo"
            />
          </a-form-item>
        </a-col>
        <a-col :md="8">
          <a-form-item
            :rules="[{ required: true, message: '类名不能为空', whitespace: true }]"
            label="类名"
            name="className"
          >
            <a-input
              v-model:value="dataForm.className"
              :maxlength="50"
              placeholder="英文名如SysDemo"
            />
          </a-form-item>
        </a-col>
        <a-col :md="8">
          <a-form-item
            :rules="[{ required: true, type: 'number', message: '模板不能为空', whitespace: true }]"
            label="生成模板"
            name="templateType"
          >
            <a-select
              v-model:value="dataForm.templateType"
              :allowClear="true"
              :options="templateTypes"
              placeholder="请选择下拉数据"
              style="width: 200px"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-alert closable type="info">
        <template #message>
          当表单类型选择
          <a-typography-text type="danger">多选</a-typography-text>
          时，查询不会生效，默认按逗号分隔保存，后台需要是字符串。特殊业务生成后梳理后按需需改。
        </template>
      </a-alert>
      <a-table
        :columns="columns"
        :data-source="tableDataSource"
        :pagination="false"
        :row-key="(record) => record.dbColumnName"
        :scroll="{ y: 500, x: 1200 }"
        bordered
        class="mt-4"
      >
        <template #fieldNameTitle>
          <a-typography-text type="danger">*</a-typography-text>
          字段名
        </template>
        <template #javaFieldNameTitle>
          <a-typography-text type="danger">*</a-typography-text>
          Java字段名
        </template>
        <template #fieldName="{ index }">
          <a-input
            v-model:value="tableDataSource[index].fieldName"
            :maxlength="6"
            :style="{ border: tableDataSource[index].fieldName ? '' : '1px solid red' }"
            placeholder="最长6"
          />
        </template>
        <template #javaFieldName="{ index }">
          <a-input
            v-model:value="tableDataSource[index].javaFieldName"
            :disabled="tableDataSource[index].defaultField"
            :maxlength="20"
            :style="{ border: tableDataSource[index].javaFieldName ? '' : '1px solid red' }"
          />
        </template>
        <template #nullable|insert|update="{ index }">
          <a-divider :dashed="true" type="vertical" />
          <a-checkbox v-model:checked="tableDataSource[index].nullable" />
          <a-divider type="vertical" />
          <a-checkbox v-model:checked="tableDataSource[index].insert" />
          <a-divider type="vertical" />
          <a-checkbox v-model:checked="tableDataSource[index].update" />
        </template>
        <template #list|sortable="{ index }">
          <a-divider :dashed="true" type="vertical" />
          <a-checkbox
            v-model:checked="tableDataSource[index].list"
            :disabled="!this.allowSearchAndListAndSortable(index)"
          />
          <a-divider type="vertical" />
          <a-checkbox
            v-model:checked="tableDataSource[index].sortable"
            :disabled="!this.allowSearchAndListAndSortable(index)"
          />
        </template>
        <template #search="{ index }">
          <a-tooltip placement="top">
            <template #title>
              <span>配合查询方式</span>
            </template>
            <a-checkbox
              v-model:checked="tableDataSource[index].search"
              :disabled="!this.allowSearchAndListAndSortable(index)"
            />
          </a-tooltip>
        </template>
        <template #queryType="{ index }">
          <a-select
            v-model:value="tableDataSource[index].queryType"
            :allowClear="true"
            :disabled="!this.allowSearchAndListAndSortable(index)"
            :options="queryTypeDicts"
            style="width: 100px"
          />
        </template>
        <template #inputType="{ index }">
          <a-select
            v-model:value="tableDataSource[index].inputType"
            :allowClear="true"
            :options="inputTypeDicts"
            style="width: 110px"
          />
        </template>
        <template #dictType="{ index }">
          <a-select
            v-model:value="tableDataSource[index].dictType"
            :allowClear="true"
            :disabled="!dictField(index)"
            :options="dictTypes"
            placeholder="可data.ts补全"
            show-search
            style="width: 140px"
          />
        </template>
        <template #sort="{ index }">
          <a-input-number
            v-model:value="tableDataSource[index].sort"
            :maxlength="20"
            :step="10"
            :style="{ border: tableDataSource[index].sort != null ? '' : '1px solid red' }"
            placeholder="升序"
          />
        </template>
      </a-table>
    </a-form>
    <br />
    <div
      :style="{
        position: 'absolute',
        left: 0,
        bottom: 0,
        width: '100%',
        borderTop: '1px solid #e9e9e9',
        padding: '10px 16px',
        background: '#fff',
        textAlign: 'left',
        zIndex: 1,
      }"
    >
      <a-button style="margin-right: 8px" @click="this.visible = false">取消</a-button>
      <a-button
        type="primary"
        @click="
          handleOk(
            this.dataForm.id === undefined ? '/sys/gen/save' : '/sys/gen/update/' + this.dataForm.id
          )
        "
        >保存
      </a-button>
    </div>
  </a-drawer>
</template>

<script>
  import { dataFormModalMixin } from '../../../mixins/common/data-form-mixin-modal.js';
  import { defHttp } from '../../../utils/http/axios';
  import { inputTypeDicts, queryTypeDicts, templateTypes } from './data';
  import { onMounted, ref } from 'vue';
  import { getTypes } from '../../../api/sys';

  export default {
    name: 'DataFormModal',
    mixins: [dataFormModalMixin],
    emits: ['refreshQuery'],
    setup() {
      let dictTypes = ref([]);
      onMounted(async () => {
        dictTypes.value = await getTypes();
      });
      return { templateTypes, queryTypeDicts, inputTypeDicts, dictTypes };
    },
    data() {
      return {
        columns: [
          {
            title: '列名',
            dataIndex: 'dbColumnName',
            width: 130,
            ellipsis: true,
            fixed: 'left',
          },
          {
            // title:'字段名',
            dataIndex: 'fieldName',
            slots: { customRender: 'fieldName', title: 'fieldNameTitle' },
            width: 150,
            fixed: 'left',
          },
          {
            title: 'DB类型',
            dataIndex: 'columnType',
            width: 130,
            ellipsis: true,
          },
          {
            title: 'Java类型',
            dataIndex: 'javaType',
            width: 100,
            ellipsis: true,
          },
          {
            //title: 'Java字段名称',
            dataIndex: 'javaFieldName',
            slots: { customRender: 'javaFieldName', title: 'javaFieldNameTitle' },
            width: 150,
          },
          {
            title: '可空 | 插入 | 更新',
            dataIndex: 'nullable|insert|update',
            width: 145,
            slots: { customRender: 'nullable|insert|update' },
          },
          {
            title: '列表 | 排序',
            dataIndex: 'list|sortable',
            width: 105,
            slots: { customRender: 'list|sortable' },
          },
          {
            title: '查询',
            dataIndex: 'search',
            width: 65,
            slots: { customRender: 'search' },
          },
          {
            title: '查询方式',
            dataIndex: 'queryType',
            width: 130,
            slots: { customRender: 'queryType' },
          },
          {
            title: '表单类型',
            dataIndex: 'inputType',
            width: 150,
            slots: { customRender: 'inputType' },
          },
          {
            title: '字典',
            dataIndex: 'dictType',
            width: 160,
            slots: { customRender: 'dictType' },
          },
          {
            title: '顺序',
            dataIndex: 'sort',
            width: 130,
            slots: { customRender: 'sort' },
          },
        ],
        tableDataSource: [],
      };
    },
    methods: {
      allowSearchAndListAndSortable(index) {
        return !['RICH_TEXT', 'IMAGE', 'FILE'].includes(this.tableDataSource[index].inputType);
      },
      dictField(index) {
        return ['SELECT', 'SELECT_MULTIPLE', 'RADIO', 'CHECKBOX'].includes(
          this.tableDataSource[index].inputType
        );
      },
      edit(title, id) {
        this.visible = true;
        this.title = title;
        defHttp.get({ url: '/sys/gen/query/' + id }).then((data) => {
          this.dataForm = data;
          this.dataForm.id = id;
          this.tableDataSource = data.columnPlans;
        });
      },
      gen(title, tableName) {
        if (!tableName) {
          this.$message.warning('请选择数据库表');
          return false;
        }
        this.visible = true;
        this.title = title;
        defHttp.get({ url: '/sys/gen/query', params: { tableName: tableName } }).then((data) => {
          this.dataForm = data;
          this.tableDataSource = data.columnPlans;
        });
      },
      // 保存后回调
      handleOkCb() {
        this.$emit('refreshQuery');
      },
      handleOk(url) {
        this.$refs.dataForm.validate().then(() => {
          // check
          const result = this.tableDataSource.some((v) => {
            if (!v.fieldName || !v.javaFieldName) {
              this.$message.error('表格红框数据必填');
              return true;
            }
          });
          if (result) {
            return;
          }
          this.dataForm.columnPlans = this.tableDataSource;
          defHttp.post({ url: url, params: this.dataForm }).then(() => {
            this.$message.success('操作成功');
            this.handleOkCb();
            this.visible = false;
          });
        });
      },
    },
  };
</script>
