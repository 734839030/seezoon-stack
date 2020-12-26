import request from '@/utils/request.js'

export function qryPage(params) {
    return request.post('/sys/param/query',params);
}

export function save(params) {
    return request.post(params.id === undefined ? '/sys/param/save': '/sys/param/update',params);
}

