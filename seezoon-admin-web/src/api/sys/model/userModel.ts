/**
 * @description: Login interface parameters
 */
import { UserInfo } from '/#/store';

export interface LoginParams {
  username: string;
  password: string;
  rememberMe?: boolean;
}

/**
 * @description: Get user information
 */
export interface GetUserInfoByUserIdParams {
  userId: string | number;
}

export interface RoleInfo {
  roleName: string;
  value: string;
}

/**
 * @description: Login interface return value
 */
export interface LoginResultModel {
  userId: string | number;
  token: string;
  role: RoleInfo;
}

/**
 * @description: Get user information return value
 */
export type GetUserInfoModel = UserInfo;
