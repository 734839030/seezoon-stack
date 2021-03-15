import type { GetUserInfoModel, LoginParams } from '/@/api/sys/model/userModel';

import store from '/@/store/index';
import { Action, getModule, Module, Mutation, VuexModule } from 'vuex-module-decorators';
import { hotModuleUnregisterModule } from '/@/utils/helper/vuexHelper';

import { PageEnum } from '/@/enums/pageEnum';
import { RoleEnum } from '/@/enums/roleEnum';
import { ROLES_KEY, TOKEN_KEY, USER_INFO_KEY } from '/@/enums/cacheEnum';

import { useMessage } from '/@/hooks/web/useMessage';

import router from '/@/router';

import { getUserInfo, loginApi, logout } from '/@/api/sys/user';

import { useI18n } from '/@/hooks/web/useI18n';
import { ErrorMessageMode } from '/@/utils/http/axios/types';
import { getAuthCache, setAuthCache } from '/@/utils/auth/index';

//import { LoginResultModel } from '/@/api/sys/model/userModel';

export type UserInfo = Omit<GetUserInfoModel, 'roles'>;

const NAME = 'app-user';
hotModuleUnregisterModule(NAME);

@Module({ namespaced: true, name: NAME, dynamic: true, store })
class User extends VuexModule {
  // user info
  private userInfoState: UserInfo | null = null;

  // token
  private tokenState = '';

  // roleList
  private roleListState: RoleEnum[] = [];

  get getUserInfoState(): UserInfo {
    return this.userInfoState || getAuthCache<UserInfo>(USER_INFO_KEY) || {};
  }

  get getTokenState(): string {
    return this.tokenState || getAuthCache<string>(TOKEN_KEY);
  }

  get getRoleListState(): RoleEnum[] {
    return this.roleListState.length > 0 ? this.roleListState : getAuthCache<RoleEnum[]>(ROLES_KEY);
  }

  @Mutation
  commitResetState(): void {
    this.userInfoState = null;
    this.tokenState = '';
    this.roleListState = [];
  }

  @Mutation
  commitUserInfoState(info: UserInfo): void {
    this.userInfoState = info;
    setAuthCache(USER_INFO_KEY, info);
  }

  @Mutation
  commitRoleListState(roleList: RoleEnum[]): void {
    this.roleListState = roleList;
    setAuthCache(ROLES_KEY, roleList);
  }

  @Mutation
  commitTokenState(info: string): void {
    this.tokenState = info;
    setAuthCache(TOKEN_KEY, info);
  }

  /**
   * @description: login
   */
  @Action
  async login(
    params: LoginParams & {
      goHome?: boolean;
      mode?: ErrorMessageMode;
    }
  ): Promise<GetUserInfoModel | null> {
    try {
      const { goHome = true, ...loginParams } = params;
      // 同步登陆
      await loginApi(loginParams);
      //const data = await loginApi(loginParams);
      //const { token, userId } = data;
      // save token
      this.commitTokenState(new Date().getTime() + '');

      // get user info
      // const userInfo = await this.getUserInfoAction({ userId });
      await this.getUserInfoAction();
      goHome && (await router.replace(PageEnum.BASE_HOME));
      return null;
    } catch (error) {
      return null;
    }
  }

  @Action
  async getUserInfoAction() {
    // 获取菜单
    const userInfo = await getUserInfo();
    //const { roles } = userInfo;
    //const roleList = roles as RoleEnum[];
    this.commitUserInfoState(userInfo);
    //  this.commitRoleListState(roleList);
    return userInfo;
  }

  /**
   * @description: logout
   */
  @Action
  async logout(goLogin = false) {
    await logout();
    goLogin && router.push(PageEnum.BASE_LOGIN);
  }

  /**
   * @description: Confirm before logging out
   */
  @Action
  async confirmLoginOut() {
    const { createConfirm } = useMessage();
    const { t } = useI18n();
    createConfirm({
      iconType: 'warning',
      title: t('sys.app.logoutTip'),
      content: t('sys.app.logoutMessage'),
      onOk: async () => {
        await this.logout(true);
      },
    });
  }
}

export const userStore = getModule<User>(User);
