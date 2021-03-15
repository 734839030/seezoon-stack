/**
 * @description: Login interface parameters
 */
export interface LoginParams {
  username: string;
  password: string;
  rememberMe: boolean;
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
export interface GetUserInfoModel {
  permissions?: string[];

  roles?: string[];
  // 用户id
  userId: string | number;
  // 用户名
  username: string;
  deptName?: string;
  // 真实名字
  name: string;
  photo?: string;
  photoUrl?: string;
  // 介绍
  desc?: string;
}
