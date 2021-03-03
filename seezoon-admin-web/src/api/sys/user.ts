import { defHttp } from '/@/utils/http/axios';
import {
  LoginParams,
  LoginResultModel,
  GetUserInfoByUserIdParams,
  GetUserInfoModel,
} from './model/userModel';

enum Api {
  Login = '/login',
  Logout = '/logout',
  GetUserInfo = '/sys/user/getInfo',
  GetPermCodeByUserId = '/getPermCodeByUserId',
}

/**
 * @description: user login api
 */
export function loginApi(params: LoginParams) {
  return defHttp.postForm<LoginResultModel>(Api.Login, params);
}
/**
 * @description: user logout
 */
export function logout() {
  return defHttp.post<any>({ url: Api.Logout });
}
/**
 * @description: getUserInfo
 */
export function getUserInfo() {
  return defHttp.get<GetUserInfoModel>({ url: Api.GetUserInfo });
}

export function getPermCodeByUserId(params: GetUserInfoByUserIdParams) {
  return defHttp.request<string[]>({
    url: Api.GetPermCodeByUserId,
    method: 'GET',
    params,
  });
}
