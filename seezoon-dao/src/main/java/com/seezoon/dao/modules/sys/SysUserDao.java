package com.seezoon.dao.modules.sys;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.seezoon.dao.framework.CrudDao;
import com.seezoon.dao.modules.sys.entity.SysUser;

/**
 * 用户信息
 * @author seezoon-generator 2021年1月16日 下午11:55:54
 */
@Repository
@Mapper
public interface SysUserDao extends CrudDao<SysUser, Integer> {

}