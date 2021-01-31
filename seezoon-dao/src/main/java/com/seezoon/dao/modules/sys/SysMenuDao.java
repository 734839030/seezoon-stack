package com.seezoon.dao.modules.sys;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.seezoon.dao.framework.CrudDao;
import com.seezoon.dao.modules.sys.entity.SysMenu;

/**
 * 菜单管理
 * @author seezoon-generator 2021年1月31日 上午12:21:33
 */
@Repository
@Mapper
public interface SysMenuDao extends CrudDao<SysMenu, Integer> {

}