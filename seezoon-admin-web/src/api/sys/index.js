import request from "@/utils/request";
import qs from 'qs'

/**
 * 部门树
 * @param parentId
 * @param includeChild
 * @returns {Promise<AxiosResponse<any>>}
 */
export function deptTree(parentId, includeChild) {
    return request.post('/sys/dept/tree', qs.stringify({parentId: parentId, includeChild: includeChild}))
}

/**
 * 获取全部字典类型
 * @returns {Promise<[]>}
 */
export async function getTypes() {
    let {data} = await request.get('/sys/dict/queryTypes');
    let dictTypes = [];
    for (let type of data.values()) {
        dictTypes.push({value: type, label: type})
    }
    return dictTypes;
}