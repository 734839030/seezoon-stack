package com.seezoon.admin.modules.sys.service;

import org.springframework.stereotype.Service;

import com.seezoon.admin.framework.service.AbstractCrudService;
import com.seezoon.dao.modules.sys.SysUserDao;
import com.seezoon.dao.modules.sys.entity.SysUser;

/**
 * 用户信息
 * @author seezoon-generator 2021年1月16日 下午11:55:54
 */
@Service
public class SysUserService extends AbstractCrudService<SysUserDao, SysUser, Integer> {

}
