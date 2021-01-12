package com.seezoon.dao.modules.sys;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.seezoon.dao.framework.CrudDao;
import com.seezoon.dao.modules.sys.entity.SysDept;

/**
 * 组织机构
 * @author seezoon-generator 2021年1月12日 下午10:54:44
 */
@Repository
@Mapper
public interface SysDeptDao extends CrudDao<SysDept, Integer> {

}