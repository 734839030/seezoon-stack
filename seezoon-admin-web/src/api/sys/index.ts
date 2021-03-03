import { defHttp } from '/@/utils/http/axios';
import { Dict } from '/@/api/sys/model/dictModel';

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
  const data = await defHttp.get({ url: '/sys/dict/queryTypes' });
  const dictTypes: Dict[] = [];
  for (const type of data.values()) {
    dictTypes.push({ label: type, value: type });
  }
  return dictTypes;
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
