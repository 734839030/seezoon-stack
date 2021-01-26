package com.seezoon.dao.modules.sys;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.seezoon.dao.framework.CrudDao;
import com.seezoon.dao.modules.sys.entity.SysUserRole;

/**
 * 用户-角色
 *
 * @author seezoon-generator 2021年1月26日 下午11:14:43
 */
@Repository
@Mapper
public interface SysUserRoleDao extends CrudDao<SysUserRole, Integer> {

}