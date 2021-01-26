/**
 * 字典数据结构
 */
export class Dict {
    constructor(label, value, disabled) {
        this.label = label
        this.value = value
        this.disabled = disabled
    }
}


/**
 * 字段数组转map
 */
export function dictArray2Map(dictArray) {
    return new Map(dictArray.map((dict, key) => [dict.value, dict.label]));
}
