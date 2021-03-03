import type { AppRouteModule } from '/@/router/types';

import { LAYOUT } from '/@/router/constant';

const sys: AppRouteModule = {
  path: '/sys',
  name: 'sys',
  component: LAYOUT,
  meta: {
    icon: 'ion:settings-outline',
    title: '系统管理',
  },
  children: [
    {
      path: 'param',
      name: 'param',
      component: () => import('/@/views/sys/param/index.vue'),
      meta: {
        icon: 'zmdi:code-setting',
        title: '系统参数',
      },
    },
    {
      path: 'dict',
      name: 'dict',
      component: () => import('/@/views/sys/dict/index.vue'),
      meta: {
        icon: 'raphael:books',
        title: '系统字典',
      },
    },
    {
      path: 'dept',
      name: 'dept',
      component: () => import('/@/views/sys/dept/index.vue'),
      meta: {
        icon: 'clarity:organization-line',
        title: '部门管理',
      },
    },
    {
      path: 'user',
      name: 'user',
      component: () => import('/@/views/sys/user/index.vue'),
      meta: {
        icon: 'ant-design:user-switch-outlined',
        title: '用户管理',
      },
    },
    {
      path: 'role',
      name: 'role',
      component: () => import('/@/views/sys/role/index.vue'),
      meta: {
        icon: 'carbon:user-role',
        title: '角色管理',
      },
    },
    {
      path: 'menu',
      name: 'menu',
      component: () => import('/@/views/sys/menu/index.vue'),
      meta: {
        icon: 'ic:sharp-menu-book',
        title: '菜单管理',
      },
    },
    {
      path: 'file',
      name: 'file',
      component: () => import('/@/views/sys/file/index.vue'),
      meta: {
        icon: 'akar-icons:file',
        title: '文件管理',
      },
    },
  ],
};

export default sys;
