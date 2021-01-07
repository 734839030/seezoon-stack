import request from "@/utils/request";

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