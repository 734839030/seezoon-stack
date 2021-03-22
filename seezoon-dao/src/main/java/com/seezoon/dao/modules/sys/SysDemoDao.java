package com.seezoon.dao.modules.sys;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.seezoon.dao.framework.CrudDao;
import com.seezoon.dao.modules.sys.entity.SysDemo;

/**
 * 生成案例
 * @author seezoon-generator 2021年3月16日 上午1:16:00
 */
@Repository
@Mapper
public interface SysDemoDao extends CrudDao<SysDemo, Integer> {

}