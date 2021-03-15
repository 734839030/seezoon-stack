import type { AppRouteModule } from '/@/router/types';

import { LAYOUT } from '/@/router/constant';

const sys: AppRouteModule = {
  path: '/',
  name: 'sys',
  component: LAYOUT,
  meta: {
    icon: 'ion:settings-outline',
    title: '系统管理',
  },
  children: [
    {
      path: '/sys/home',
      name: '/sys/home',
      component: () => import('/@/views/sys/home/index.vue'),
      meta: {
        title: '首页',
        icon: 'bx:bx-home',
      },
    },
    {
      path: '/sys/dept',
      name: '/sys/dept',
      component: () => import('/@/views/sys/dept/index.vue'),
      meta: {
        icon: 'clarity:organization-line',
        title: '部门管理',
      },
    },
    {
      path: '/sys/user',
      name: '/sys/user',
      component: () => import('/@/views/sys/user/index.vue'),
      meta: {
        icon: 'ant-design:user-switch-outlined',
        title: '用户管理',
      },
    },
    {
      path: '/sys/role',
      name: '/sys/role',
      component: () => import('/@/views/sys/role/index.vue'),
      meta: {
        icon: 'carbon:user-role',
        title: '角色管理',
      },
    },
    {
      path: '/sys/menu',
      name: '/sys/menu',
      component: () => import('/@/views/sys/menu/index.vue'),
      meta: {
        icon: 'ic:sharp-menu-book',
        title: '菜单管理',
      },
    },
    {
      path: '/sys/param',
      name: '/sys/param',
      component: () => import('/@/views/sys/param/index.vue'),
      meta: {
        icon: 'zmdi:code-setting',
        title: '系统参数',
      },
    },
    {
      path: '/sys/dict',
      name: '/sys/dict',
      component: () => import('/@/views/sys/dict/index.vue'),
      meta: {
        icon: 'raphael:books',
        title: '系统字典',
      },
    },
    {
      path: '/sys/file',
      name: '/sys/file',
      component: () => import('/@/views/sys/file/index.vue'),
      meta: {
        icon: 'akar-icons:file',
        title: '文件管理',
      },
    },
    {
      path: '/doc',
      name: 'doc',
      component: () => import('/@/views/sys/iframe/FrameBlank.vue'),
      meta: {
        frameSrc: 'https://vvbin.cn/docs/',
        icon: 'akar-icons:file',
        title: '内部',
      },
    },
    {
      path: 'https://vvbin.cn/docs/',
      name: 'docex',
      component: () => import('/@/views/sys/iframe/FrameBlank.vue'),
      meta: {
        icon: 'akar-icons:file',
        title: '外部',
      },
    },
  ],
};

export default sys;
