import { defHttp } from '/@/utils/http/axios';
import { Dict } from '/@/api/sys/model/dictModel';
import { ref } from 'vue';
import { message } from 'ant-design-vue';
import { Persistent } from '/@/utils/cache/persistent';
import { DICT_KEY } from '/@/enums/cacheEnum';

/**
 * 部门树
 * @param parentId
 * @param includeChild
 */
export function deptTree(parentId: number, includeChild?: boolean) {
  return defHttp.postForm('/sys/dept/tree', { parentId: parentId, includeChild: includeChild });
}

/**
 * menu树
 * @param parentId
 * @param includeChild
 */
export function menuTree(parentId, includeChild) {
  return defHttp.postForm('/sys/menu/tree', { parentId: parentId, includeChild: includeChild });
}

/**
 * 获取全部字典类型
 * @returns {Promise<[]>}
 */
export async function getTypes() {
  const data = await defHttp.get({ url: '/sys/dict/query_types' });
  const dictTypes: Dict[] = [];
  for (const type of data.values()) {
    dictTypes.push({ label: type, value: type });
  }
  return dictTypes;
}

/**
 * 获取指定字典数据,作废
 */
export function getDictRomote(type: string) {
  const dicts = ref([]);
  defHttp.get({ url: '/sys/dict/query_by_type', params: { type: type } }).then((data) => {
    dicts.value = data;
  });
  return dicts;
}

let globalDicts;

/**
 * 在表单回显的时候会区分类型
 * @param type
 * @param number 是否数值
 */
export function getDict(type: string, number = false): Dict[] {
  const dictArray = globalDicts.get(type) || [];
  // 转数值类字段
  if (number) {
    dictArray.forEach((dict) => {
      dict.value = parseInt(dict.value);
    });
  }
  return dictArray;
}

export function getDictAll() {
  const dictMap = new Map<string, Dict[]>();
  const dictSession = Persistent.getSession<Dict[]>(DICT_KEY) || [];

  for (const dict of dictSession.values()) {
    const type = <string>dict.type;
    if (dictMap.has(type)) {
      dictMap.get(type)?.push({ label: dict.label, value: dict.value, disabled: dict.disabled });
    } else {
      dictMap.set(type, [{ label: dict.label, value: dict.value, disabled: dict.disabled }]);
    }
  }

  return dictMap;
}

/**
 * 加载全量字典
 */
export async function initAllDict() {
  try {
    const data = await defHttp.get({ url: '/sys/dict/query_by_type' });
    const dicts = <Dict[]>[];
    for (const dict of data.values()) {
      dicts.push({
        label: dict.name,
        value: dict.code,
        type: dict.type,
        disabled: dict.status === 0,
      });
    }
    Persistent.setSession(DICT_KEY, dicts, true);
    globalDicts = <Map<string, Dict[]>>getDictAll();
  } catch (e) {
    message.error('字典加载失败请刷新网页:' + e);
  }
}

/**
 * 获取全部角色
 */
export async function getRoles() {
  const data = await defHttp.get({ url: '/sys/role/query' });
  const roles: Dict[] = [];
  for (const role of data.values()) {
    roles.push({ label: role.name, value: role.id, disabled: role.status === 0 });
  }
  return roles;
}

/**
 * 字段数组转map
 */
export function dictArray2Map(dictArray: Dict[]) {
  return new Map(dictArray.map((dict) => [dict.value, dict.label]));
}
