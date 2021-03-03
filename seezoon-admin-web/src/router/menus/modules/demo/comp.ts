import type { MenuModule } from '/@/router/types';
import { t } from '/@/hooks/web/useI18n';

const menu: MenuModule = {
  orderNo: 30,
  menu: {
    name: t('routes.demo.comp.comp'),
    path: '/comp',
    tag: {
      dot: true,
    },
    children: [
      {
        path: 'basic',
        name: t('routes.demo.comp.basic'),
      },
      {
        path: 'form',
        name: t('routes.demo.form.form'),

        children: [
          {
            path: 'basic',
            name: t('routes.demo.form.basic'),
          },
          {
            path: 'useForm',
            name: t('routes.demo.form.useForm'),
          },
          {
            path: 'refForm',
            name: t('routes.demo.form.refForm'),
          },
          {
            path: 'advancedForm',
            name: t('routes.demo.form.advancedForm'),
          },
          {
            path: 'ruleForm',
            name: t('routes.demo.form.ruleForm'),
          },
          {
            path: 'dynamicForm',
            name: t('routes.demo.form.dynamicForm'),
          },
          {
            path: 'customerForm',
            name: t('routes.demo.form.customerForm'),
          },
        ],
      },
      {
        path: 'table',
        name: t('routes.demo.table.table'),
        tag: {
          dot: true,
        },
        children: [
          {
            path: 'basic',
            name: t('routes.demo.table.basic'),
          },
          {
            path: 'treeTable',
            name: t('routes.demo.table.treeTable'),
          },
          {
            path: 'fetchTable',
            name: t('routes.demo.table.fetchTable'),
          },
          {
            path: 'fixedColumn',
            name: t('routes.demo.table.fixedColumn'),
          },
          {
            path: 'customerCell',
            name: t('routes.demo.table.customerCell'),
          },
          {
            path: 'formTable',
            name: t('routes.demo.table.formTable'),
          },
          {
            path: 'useTable',
            name: t('routes.demo.table.useTable'),
          },
          {
            path: 'refTable',
            name: t('routes.demo.table.refTable'),
          },
          {
            path: 'multipleHeader',
            name: t('routes.demo.table.multipleHeader'),
          },
          {
            path: 'mergeHeader',
            name: t('routes.demo.table.mergeHeader'),
          },
          {
            path: 'expandTable',
            name: t('routes.demo.table.expandTable'),
          },
          {
            path: 'fixedHeight',
            name: t('routes.demo.table.fixedHeight'),
          },
          {
            path: 'footerTable',
            name: t('routes.demo.table.footerTable'),
          },
          {
            path: 'editCellTable',
            name: t('routes.demo.table.editCellTable'),
            tag: {
              dot: true,
            },
          },
          {
            path: 'editRowTable',
            name: t('routes.demo.table.editRowTable'),
            tag: {
              dot: true,
            },
          },
        ],
      },
      {
        path: 'countTo',
        name: t('routes.demo.comp.countTo'),
      },
      {
        path: 'transition',
        name: t('routes.demo.comp.transition'),
      },

      {
        path: 'modal',
        name: t('routes.demo.comp.modal'),
      },
      {
        path: 'drawer',
        name: t('routes.demo.comp.drawer'),
      },
      {
        path: 'desc',
        name: t('routes.demo.comp.desc'),
      },
      {
        path: 'qrcode',
        name: t('routes.demo.comp.qrcode'),
      },
      {
        path: 'strength-meter',
        name: t('routes.demo.comp.strength'),
      },
      {
        path: 'upload',
        name: t('routes.demo.comp.upload'),
      },
      {
        path: 'loading',
        name: t('routes.demo.comp.loading'),
      },
      {
        path: 'tree',
        name: t('routes.demo.comp.tree'),
        children: [
          {
            path: 'basic',
            name: t('routes.demo.comp.treeBasic'),
          },
          {
            path: 'editTree',
            name: t('routes.demo.comp.editTree'),
          },
          {
            path: 'actionTree',
            name: t('routes.demo.comp.actionTree'),
          },
        ],
      },
      {
        name: t('routes.demo.editor.editor'),
        path: 'editor',
        tag: {
          content: 'new',
        },
        children: [
          {
            path: 'markdown',
            name: t('routes.demo.editor.markdown'),
            children: [
              {
                path: 'index',
                name: t('routes.demo.editor.tinymceBasic'),
              },
              {
                path: 'editor',
                name: t('routes.demo.editor.tinymceForm'),
              },
            ],
          },
          {
            path: 'tinymce',
            name: t('routes.demo.editor.tinymce'),
            children: [
              {
                path: 'index',
                name: t('routes.demo.editor.tinymceBasic'),
              },
              {
                path: 'editor',
                name: t('routes.demo.editor.tinymceForm'),
              },
            ],
          },
        ],
      },
      {
        path: 'scroll',
        name: t('routes.demo.comp.scroll'),
        children: [
          {
            path: 'basic',
            name: t('routes.demo.comp.scrollBasic'),
          },
          {
            path: 'action',
            name: t('routes.demo.comp.scrollAction'),
          },
          {
            path: 'virtualScroll',
            name: t('routes.demo.comp.virtualScroll'),
          },
        ],
      },
      {
        path: 'lazy',
        name: t('routes.demo.comp.lazy'),
        children: [
          {
            path: 'basic',
            name: t('routes.demo.comp.lazyBasic'),
          },
          {
            path: 'transition',
            name: t('routes.demo.comp.lazyTransition'),
          },
        ],
      },
      {
        path: 'verify',
        name: t('routes.demo.comp.verify'),
        children: [
          {
            path: 'drag',
            name: t('routes.demo.comp.verifyDrag'),
          },
          {
            path: 'rotate',
            name: t('routes.demo.comp.verifyRotate'),
          },
        ],
      },
    ],
  },
};
export default menu;
