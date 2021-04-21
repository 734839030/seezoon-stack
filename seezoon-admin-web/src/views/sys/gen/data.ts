import { Dict } from '/@/api/sys/model/dictModel';
import { defHttp } from '/@/utils/http/axios';
import { ref } from 'vue';
import { dictArray2Map } from '/@/api/sys';

export function getTables() {
  const tableDicts = ref<Dict[]>([]);
  defHttp.get({ url: '/sys/gen/tables' }).then((data) => {
    tableDicts.value = data.map(function (item) {
      return { label: item, value: item };
    });
  });
  return tableDicts;
}

export const templateTypes: Dict[] = [
  {
    label: '基础增删改查',
    value: 0,
  },
  {
    label: '树结构',
    value: 1,
    disabled: true,
  },
];

export const templateTypesMap = dictArray2Map(templateTypes);

export const queryTypeDicts: Dict[] = [
  { label: 'none', value: 'NONE' },
  { label: '=', value: 'EQUAL' },
  { label: '!=', value: 'NOT_EQUAL' },
  { label: '>=', value: 'GREATER_EQUAL' },
  { label: '>', value: 'GREATER' },
  { label: '<=', value: 'LESS_EQUAL' },
  { label: '<', value: 'LESS' },
  { label: 'like', value: 'LIKE' },
  { label: 'left like', value: 'LEFT_LIKE' },
  { label: 'right like', value: 'RIGHT_LIKE' },
];

export const inputTypeDicts: Dict[] = [
  { label: 'none', value: 'NONE' },
  { label: '文本框', value: 'TEXT' },
  { label: '下拉框', value: 'SELECT' },
  { label: '下拉多选', value: 'SELECT_MULTIPLE' }, // 组件 tags 模式，分词多选
  { label: '隐藏域', value: 'HIDDEN' },
  { label: '整数', value: 'INTEGRAL_NUMBER' },
  { label: '小数', value: 'DECIMAL' },
  { label: '单选', value: 'RADIO' },
  { label: '复选框', value: 'CHECKBOX' },
  { label: '日期', value: 'DATE' },
  { label: '时间', value: 'DATETIME' },
  { label: '文本域', value: 'TEXTAREA' },
  { label: '富文本', value: 'RICH_TEXT' },
  { label: '图片', value: 'IMAGE' },
  { label: '文件', value: 'FILE' },
];
