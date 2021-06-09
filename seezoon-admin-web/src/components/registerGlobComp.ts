import {Icon} from './Icon';

import { App } from 'vue';
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/antd.css';

const compList = [Icon];

export function registerGlobComp(app: App) {
  compList.forEach((comp: any) => {
    app.component(comp.name || comp.displayName, comp);
  });
  app.use(Antd);
}
