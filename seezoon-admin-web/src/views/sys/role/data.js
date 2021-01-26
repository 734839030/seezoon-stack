import {Dict, dictArray2Map} from "@/utils/dict";

export var dataScopeArray = [
    new Dict('全部', 0),
    new Dict('本人', 1),
    new Dict('本部门', 2),
    new Dict('本部门及所有下级部门', 3),
    new Dict('所有下级部门', 4),
    new Dict('指定部门', 5)
]

export var dataScopeMap = dictArray2Map(dataScopeArray)
