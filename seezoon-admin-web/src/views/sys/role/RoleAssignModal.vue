<template>
  <a-drawer
    v-model:visible="visible"
    :destroyOnClose="true"
    placement="right"
    :title="(this.addUser ? '可分配角色' : '已分配角色') + '【' + this.roleName + '】'"
    :width="1050"
  >
    <!-- 查询表单 -->
    <a-form
      ref="searchForm"
      :model="searchForm"
      layout="inline"
      :labelCol="this.labelCol"
      :wrapperCol="this.wrapperCol"
    >
      <a-form-item label="登录名" name="username">
        <a-input v-model:value="searchForm.username" :maxlength="50" placeholder="请输入登录名" />
      </a-form-item>
      <a-form-item label="姓名" name="name">
        <a-input v-model:value="searchForm.name" :maxlength="50" placeholder="请输入姓名" />
      </a-form-item>
      <a-form-item label="手机号" name="mobile">
        <a-input v-model:value="searchForm.mobile" :maxlength="20" placeholder="请输入手机号" />
      </a-form-item>
      <a-form-item>
        <a-space>
          <a-button type="primary" @click="handleQuery()">查询</a-button>
          <a-button
            :disabled="this.userTableSelectedRowKeys.length === 0"
            type="default"
            @click="handleAssign()"
            >{{ this.addUser ? '分配' : '移除' }}
          </a-button>
          <a-button
            v-if="!this.addUser"
            type="default"
            @click="
              this.userTableSelectedRowKeys = [];
              this.searchForm.hasThisRole = false;
              this.handleQuery();
            "
          >
            可分配用户
          </a-button>
          <a-button
            v-else
            type="default"
            @click="
              this.userTableSelectedRowKeys = [];
              this.searchForm.hasThisRole = true;
              this.handleQuery();
            "
          >
            查看已分配
          </a-button>
        </a-space>
      </a-form-item>
    </a-form>
    <a-table
      :columns="columns"
      :data-source="data"
      :loading="loading"
      :pagination="pagination"
      :row-key="(record) => record.id"
      :rowSelection="{
        fixed: true,
        selectedRowKeys: userTableSelectedRowKeys,
        onChange: userTableSelectedRowChange,
      }"
      bordered
      class="mt-4"
      size="small"
      @change="handleTableChange"
    />
  </a-drawer>
</template>

<script>
  import { queryTableMixin } from '../../../mixins/common/query-table-mixin';
  import { defHttp } from '../../../utils/http/axios';

  export default {
    name: 'RoleAssignModal',
    mixins: [queryTableMixin],
    data() {
      return {
        visible: false,
        roleId: undefined,
        roleName: undefined,
        userTableSelectedRowKeys: [],
        url: `/sys/user/query`,
        columns: [
          {
            title: '登录名',
            dataIndex: 'username',
          },
          {
            title: '姓名',
            dataIndex: 'name',
          },
          {
            title: '部门',
            dataIndex: 'deptName',
          },
          {
            title: '手机号',
            dataIndex: 'mobile',
          },
        ],
      };
    },
    computed: {
      addUser() {
        return !this.searchForm.hasThisRole;
      },
    },
    methods: {
      show(roleId, roleName) {
        this.visible = true;
        this.roleId = roleId;
        this.roleName = roleName;
        this.searchForm.roleId = roleId;
        this.searchForm.hasThisRole = true;
        this.handleQuery();
      },
      handleAssign() {
        //处理分配
        if (this.userTableSelectedRowKeys.length == 0) {
          this.$message.info('请选择待用户');
        } else {
          defHttp
            .post({
              url: '/sys/role/assign',
              params: {
                userIds: this.userTableSelectedRowKeys,
                roleId: this.roleId,
                addUser: this.addUser,
              },
            })
            .then(() => {
              this.userTableSelectedRowKeys = [];
              this.handleQuery();
              this.$message.success('操作成功');
            });
        }
      },
      userTableSelectedRowChange(selectedRowKeys) {
        this.userTableSelectedRowKeys = selectedRowKeys;
      },
    },
  };
</script>
