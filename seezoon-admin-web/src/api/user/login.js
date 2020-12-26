import request from '@/utils/request.js'
import qs from 'qs' 

export function login({username,password,rememberMe}){
    return request.post('/login',
    qs.stringify({username:username,password:password,rememberMe:rememberMe}));
}