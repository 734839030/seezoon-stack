import { Dict } from '/@/api/sys/model/dictModel';
import { dictArray2Map, getDict } from '/@/api/sys';

// value和后台类型要对应
export const inputSelectDicts: Dict[] = getDict('select');
export const inputSelectDictsMap = dictArray2Map(inputSelectDicts);

export const inputRadioDicts: Dict[] = [
  { value: '1', label: '单选' },
  { value: '2', label: '单选2' },
];

export const inputRadioDictsMap = dictArray2Map(inputRadioDicts);

export const inputCheckboxDicts: Dict[] = [
  { value: '1', label: '多选1' },
  { value: '2', label: '多选2' },
];

export const inputCheckboxDictsMap = dictArray2Map(inputCheckboxDicts);

export const statusDicts: Dict[] = getDict('status', true);

export const statusDictsMap = dictArray2Map(statusDicts);
