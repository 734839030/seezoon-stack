import { Dict } from '/@/api/sys/model/dictModel';
import { dictArray2Map } from '/@/api/sys';

// 提示: 字典的value 类型需要和后台实际返回的数据类型一致，不然在数据回显的时候，无法自自动选中，校验也会不生效

export const resultDicts: Dict[] = [
  { value: 0, label: '成功' },
  { value: 10, label: '账号不存在' },
  { value: 20, label: '密码错误' },
  { value: 30, label: '禁用' },
  { value: 40, label: '账户已锁定' },
  { value: 45, label: 'IP已锁定' },
  { value: 50, label: '未知失败' },
];
export const resultDictsMap = dictArray2Map(resultDicts);
