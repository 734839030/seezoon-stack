<template>
  <PageWrapper contentBackground contentClass="p-4" title="按钮权限控制">
    <Alert message="刷新后会还原" show-icon />

    <CurrentPermissionMode />

    <p>
      当前拥有的code列表: <a> {{ permissionStore.getPermCodeListState }} </a>
    </p>
    <Divider />
    <Alert class="mt-4" message="点击后请查看按钮变化" show-icon type="info" />
    <Divider />
    <a-button class="mr-2" type="primary" @click="changePermissionCode('2')">
      点击切换按钮权限(用户id为2)
    </a-button>
    <a-button type="primary" @click="changePermissionCode('1')">
      点击切换按钮权限(用户id为1,默认)
    </a-button>

    <Divider>组件方式判断权限</Divider>
    <Authority :value="'1000'">
      <a-button class="mx-4" type="primary"> 拥有code ['1000']权限可见</a-button>
    </Authority>

    <Authority :value="'2000'">
      <a-button class="mx-4" color="success"> 拥有code ['2000']权限可见</a-button>
    </Authority>

    <Authority :value="['1000', '2000']">
      <a-button class="mx-4" color="error"> 拥有code ['1000','2000']角色权限可见</a-button>
    </Authority>

    <Divider>函数方式方式判断权限</Divider>
    <a-button v-if="hasPermission('1000')" class="mx-4" type="primary">
      拥有code ['1000']权限可见
    </a-button>

    <a-button v-if="hasPermission('2000')" class="mx-4" color="success">
      拥有code ['2000']权限可见
    </a-button>

    <a-button v-if="hasPermission(['1000', '2000'])" class="mx-4" color="error">
      拥有code ['1000','2000']角色权限可见
    </a-button>

    <Divider>指令方式方式判断权限(该方式不能动态修改权限.)</Divider>
    <a-button v-auth="'1000'" class="mx-4" type="primary"> 拥有code ['1000']权限可见</a-button>

    <a-button v-auth="'2000'" class="mx-4" color="success"> 拥有code ['2000']权限可见</a-button>

    <a-button v-auth="['1000', '2000']" class="mx-4" color="error">
      拥有code ['1000','2000']角色权限可见
    </a-button>
  </PageWrapper>
</template>
<script lang="ts">
  import { defineComponent } from 'vue';
  import { Alert, Divider } from 'ant-design-vue';
  import CurrentPermissionMode from '../CurrentPermissionMode.vue';
  import { usePermission } from '/@/hooks/web/usePermission';
  import { Authority } from '/@/components/Authority';
  import { permissionStore } from '/@/store/modules/permission';
  import { PermissionModeEnum } from '/@/enums/appEnum';
  import { PageWrapper } from '/@/components/Page';
  import { getUserResources } from '/@/api/sys/user';

  export default defineComponent({
    components: { Alert, PageWrapper, CurrentPermissionMode, Divider, Authority },
    setup() {
      const { hasPermission } = usePermission();

      // !模拟从后台获取权限编码， 该函数可能只需要执行一次，实际项目可以自行放到合适的时机
      async function changePermissionCode() {
        const permissions = await getUserResources();
        permissionStore.commitPermCodeListState(permissions);
      }


      // 默认初始化为1
      changePermissionCode();
      return {
        hasPermission,
        permissionStore,
        changePermissionCode,
        PermissionModeEnum,
      };
    },
  });
</script>
<style lang="less" scoped>
  .demo {
    background: #fff;
  }
</style>
