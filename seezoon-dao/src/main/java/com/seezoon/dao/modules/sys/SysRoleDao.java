package com.seezoon.dao.modules.sys;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.seezoon.dao.framework.CrudDao;
import com.seezoon.dao.modules.sys.entity.SysRole;

/**
 * 角色表
 * @author seezoon-generator 2021年1月25日 上午12:14:26
 */
@Repository
@Mapper
public interface SysRoleDao extends CrudDao<SysRole, Integer> {

}