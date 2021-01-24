import {Dict, dictArray2Map} from "@/utils/dict";

export var dataScopeArray = [
    new Dict(0, '全部'),
    new Dict(1, '本人'),
    new Dict(2, '本部门'),
    new Dict(3, '本部门及所有下级部门'),
    new Dict(4, '所有下级部门'),
    new Dict(5, '指定部门')
]

export var dataScopeMap = dictArray2Map(dataScopeArray)
