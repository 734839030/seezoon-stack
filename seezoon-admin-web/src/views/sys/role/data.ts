import { Dict } from '/@/api/sys/model/dictModel';
import { dictArray2Map } from '/@/api/sys';

export const dataScopeArray: Dict[] = [
  { label: '全部', value: 0 },
  { label: '本人', value: 1 },
  { label: '本部门', value: 2 },
  { label: '本部门及所有下级部门', value: 3 },
  { label: '所有下级部门', value: 4 },
  { label: '所有下级部门', value: 5 },
  { label: '指定部门', value: 6 },
];

export const dataScopeMap = dictArray2Map(dataScopeArray);
