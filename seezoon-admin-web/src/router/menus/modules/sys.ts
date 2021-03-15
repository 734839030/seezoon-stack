import type { MenuModule } from '/@/router/types';

// 系统管理
const menu: MenuModule = {
  orderNo: 9,
  menu: {
    path: '/',
    name: '系统管理',
    icon: 'ion:settings-outline',
    children: [
      {
        path: '/sys/dept',
        name: '部门管理',
      },
      {
        path: '/sys/user',
        name: '用户管理',
      },
      {
        path: '/sys/role',
        name: '角色管理',
      },
      {
        path: '/sys/menu',
        name: '菜单管理',
      },
      {
        path: '/sys/param',
        name: '系统参数',
      },
      {
        path: '/sys/dict',
        name: '系统字典',
      },
      {
        path: '/sys/file',
        name: '文件管理',
      },
      {
        path: '/doc',
        name: '内部',
      },
      {
        path: 'https://vvbin.cn/docs/',
        name: '外部',
      },
    ],
  },
};
export default menu;
