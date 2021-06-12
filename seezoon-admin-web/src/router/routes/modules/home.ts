import type { AppRouteModule } from '/@/router/types';
import { LAYOUT } from '/@/router/constant';

const home: AppRouteModule = {
  path: '/sys/home',
  name: 'SysHome',
  component: LAYOUT,
  redirect: '/home',
  meta: {
    title: '首页',
    icon: 'bx:bx-home',
  },
  children: [
    {
      path: '/home',
      name: 'home',
      component: () => import('/@/views/sys/home/index.vue'),
      meta: {
        title: '首页',
        icon: 'bx:bx-home',
      },
    },
  ],
};

export default home;
