import type { MenuModule } from '/@/router/types';

// 系统管理
const menu: MenuModule = {
  orderNo: 9,
  menu: {
    path: '/sys',
    name: '系统管理',
    icon: 'ion:settings-outline',
    children: [
      {
        path: 'dept',
        name: '部门管理',
      },
      {
        path: 'user',
        name: '用户管理',
      },
      {
        path: 'role',
        name: '角色管理',
      },
      {
        path: 'menu',
        name: '菜单管理',
      },
      {
        path: 'param',
        name: '系统参数',
      },
      {
        path: 'dict',
        name: '系统字典',
      },
      {
        path: 'file',
        name: '文件管理',
      },
      {
        path: 'doc',
        name: '内部',
      },
      {
        path: 'https://doc.stack.seezoon.com',
        name: '外部',
      },
    ],
  },
};
export default menu;
