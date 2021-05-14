import { Dict } from '/@/api/sys/model/dictModel';
import { dictArray2Map } from '/@/api/sys';

export const dataScopeArray: Dict[] = [
  { label: '全部', value: 0 },
  { label: '本部门', value: 10 },
  { label: '本部门及以下', value: 20 },
  { label: '本人', value: 30 },
];

export const dataScopeMap = dictArray2Map(dataScopeArray);
