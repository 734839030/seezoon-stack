import request from "@/utils/request";
import qs from 'qs'

export function deptTree(parentId, includeChild) {
    return request.post('/sys/dept/tree', qs.stringify({parentId: parentId, includeChild: includeChild}))
}

