import type { MenuModule } from '/@/router/types';

// 系统管理
const menu: MenuModule = {
  orderNo: -1,
  menu: {
    path: '/sys',
    name: '系统管理',
    children: [
      {
        path: 'param',
        name: '系统参数',
      },
      {
        path: 'dict',
        name: '系统字典',
      },
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
        path: 'file',
        name: '文件管理',
      },
    ],
  },
};
export default menu;
