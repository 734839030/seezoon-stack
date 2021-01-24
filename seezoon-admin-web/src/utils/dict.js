/**
 * 字典数据结构
 */
export class Dict {
    constructor(code, name) {
        this.code = code
        this.name = name
    }
}

/**
 * 字段数组转map
 */
export function dictArray2Map(dictArray) {
    return new Map(dictArray.map((value, key) => [value.code, value.name]));
}
