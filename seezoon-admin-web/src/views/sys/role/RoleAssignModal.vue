<template>
  <a-modal
      v-model:visible="visible" :destroyOnClose="true" :footer="null" :maskClosable="false" :width="1200"
      title="分配角色">
    <a-space direction="vertical">
      <!-- 查询表单 -->
      <a-form ref="searchForm" :model="searchForm" layout="inline">
        <a-form-item label="登录名" name="username">
          <a-input
              v-model:value="searchForm.username" :maxlength="50" placeholder="请输入登录名">
          </a-input>
        </a-form-item>
        <a-form-item label="姓名" name="name">
          <a-input
              v-model:value="searchForm.name" :maxlength="50" placeholder="请输入姓名">
          </a-input>
        </a-form-item>
        <a-form-item label="手机号" name="mobile">
          <a-input
              v-model:value="searchForm.mobile" :maxlength="20" placeholder="请输入手机号">
          </a-input>
        </a-form-item>
        <a-form-item>
          <a-space>
            <a-button type="primary" @click="handleQueryPage()">查询</a-button>
            <a-button :disabled="this.userTableSelectedRowKeys.length === 0" type="default" @click="handleRemove()">移除
            </a-button>
            <a-button type="default" @click="handleDataForm('添加')">添加</a-button>
          </a-space>
        </a-form-item>
      </a-form>
      <a-table :columns="columns" :data-source="data" :loading="loading" :pagination="pagination"
               :row-key="(record) => record.id"
               :rowSelection="{fixed:true,selectedRowKeys:userTableSelectedRowKeys,onChange:userTableSelectedRowChange}"
               :scroll="{y: 600 }" bordered
               size="small"
               @change="handleTableChange">
      </a-table>
    </a-space>
  </a-modal>
</template>

<script>
import {pageTableMixin} from "@/mixins/common/page-table-mixin";

export default {
  name: "RoleAssignModal",
  mixins: [pageTableMixin],
  data() {
    return {
      visible: false,
      roleId: undefined,
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
        }
      ],
    }
  },
  methods: {
    show(roleId) {
      this.visible = true
      this.roleId = roleId
      this.searchForm.roleId = roleId;
      this.handleQueryPage()
    },
    handleRemove() {
      if (this.userTableSelectedRowKeys.length == 0) {
        this.$message.info("请选择待移除的用户")
      } else {
        this.$http.post('/sys/role/assign', {
          userIds: this.userTableSelectedRowKeys,
          roleId: this.roleId,
          assign: false
        }).then(() => {
          this.userTableSelectedRowKeys = []
          this.handleQueryPage();
          this.$message.success("移除成功");
        })
      }
    },
    userTableSelectedRowChange(selectedRowKeys) {
      this.userTableSelectedRowKeys = selectedRowKeys;
    }
  }
}
</script>