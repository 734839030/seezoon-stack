<template>
  <a-modal
    v-model:visible="visible"
    :confirm-loading="confirmLoading"
    :destroyOnClose="true"
    :height="this.height"
    :maskClosable="false"
    :width="this.width"
    okText="保存"
    @ok="handleOk(this.dataForm.id === undefined ? '/sys/menu/save' : '/sys/menu/update')"
  >
    <a-form
      ref="dataForm"
      :label-col="this.labelCol"
      :model="dataForm"
      :wrapper-col="this.wrapperCol"
    >
      <a-input v-model:value="dataForm.id" type="hidden" />
      <a-tabs v-model:activeKey="dataForm.type" :animated="false" @change="handleTabChange">
        <a-tab-pane v-for="pane in tabsPanes" :key="pane.key" :tab="pane.title">
          <div v-if="pane.key === dataForm.type">
            <a-row>
              <a-col :md="12" :xs="24">
                <a-form-item :rules="[]" label="上级" name="parentId">
                  <a-tree-select
                    v-model:value="dataForm.parentId"
                    :allowClear="true"
                    :load-data="loadMenuData"
                    :tree-data="menuTreeData"
                    placeholder="请选择上级"
                  />
                </a-form-item>
              </a-col>
              <a-col :md="12" :xs="24">
                <a-form-item
                  :rules="[
                    { required: true, message: '名称不能为空', whitespace: true },
                    { min: 1, max: 50, message: '名称长度1-50' },
                  ]"
                  label="名称"
                  name="name"
                >
                  <a-input v-model:value="dataForm.name" :maxlength="50" placeholder="名称" />
                </a-form-item>
              </a-col>
            </a-row>
            <a-row v-if="pane.key === 2">
              <a-col :md="12" :xs="24">
                <a-form-item
                  :rules="[
                    { required: true, message: '地址不能为空', whitespace: true },
                    { min: 1, max: 255, message: '名称长度1-255' },
                  ]"
                  label="地址"
                  name="url"
                >
                  <a-input v-model:value="dataForm.url" :maxlength="255" placeholder="地址" />
                </a-form-item>
              </a-col>
              <a-col :md="12" :xs="24">
                <a-form-item
                  :rules="[{ required: true, message: '打开位置不能为空', whitespace: true }]"
                  help="当是外链时候有效"
                  label="打开位置"
                  name="target"
                >
                  <a-radio-group v-model:value="dataForm.target" defaultValue="main">
                    <a-radio-button value="main"> 主窗口</a-radio-button>
                    <a-radio-button value="_blank"> 新窗口</a-radio-button>
                  </a-radio-group>
                </a-form-item>
              </a-col>
            </a-row>
            <a-row>
              <a-col :md="12" :xs="24">
                <a-form-item :rules="[]" label="权限" name="permission">
                  <a-input
                    v-model:value="dataForm.permission"
                    :maxlength="50"
                    placeholder="权限标识"
                  />
                </a-form-item>
              </a-col>
              <a-col :md="12" :xs="24">
                <a-form-item
                  :rules="[
                    { required: true, type: 'number', message: '请输入数字', whitespace: true },
                  ]"
                  help="顺序越小显示越前"
                  label="顺序"
                  name="sort"
                >
                  <a-input-number
                    v-model:value="dataForm.sort"
                    :max="10000"
                    :precision="0"
                    :step="10"
                    placeholder="请输入顺序"
                  />
                </a-form-item>
              </a-col>
            </a-row>
            <a-row>
              <a-col :md="12" :xs="24">
                <a-form-item :rules="[]" label="图标" name="icon">
                  <icon-picker
                    v-model:value="dataForm.icon"
                    @change="
                      (value) => {
                        dataForm.icon = value;
                      }
                    "
                  />
                  <!--
                                    <a-input v-model:value="dataForm.icon" :maxlength="50" placeholder="选择图标" />
                  -->
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
                      <a-badge status="success" text="启用" />
                    </a-radio>
                    <a-radio :value="0">
                      <a-badge status="warning" text="停用" />
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
          </div>
        </a-tab-pane>
      </a-tabs>
    </a-form>
  </a-modal>
</template>

<script>
  import { dataFormModalMixin } from '../../../mixins/common/data-form-mixin-modal';
  import { menuTreeMixin } from '../../../mixins/sys/menu-tree-mixin';
  import { IconPicker } from '../../../components/Icon';
  import { defHttp } from '../../../utils/http/axios';

  export default {
    name: 'DataFormModal',
    components: { IconPicker },
    mixins: [dataFormModalMixin, menuTreeMixin],
    emits: ['refreshQuery'],
    data() {
      return {
        tabsPanes: [
          { key: 1, title: '目录' },
          { key: 2, title: '菜单' },
          { key: 3, title: '按钮' },
        ],
      };
    },
    methods: {
      open(title, id) {
        this.visible = true;
        this.title = title;
        this.loadMenuData();
        if (null != id) {
          defHttp.get({ url: '/sys/menu/query/' + id }).then((data) => {
            if (data.parentId === 0) {
              data.parentId = undefined;
            }
            this.dataForm = data;
          });
        } else {
          this.dataForm = { status: 1, type: 1, sort: 1000, target: 'main' };
        }
      },
      // 保存后回调
      handleOkCb() {
        this.$emit('refreshQuery');
      },
      handleTabChange(key) {
        this.dataForm.type = key;
      },
    },
  };
</script>
