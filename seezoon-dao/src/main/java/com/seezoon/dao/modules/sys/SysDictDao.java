package com.seezoon.dao.modules.sys;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.seezoon.dao.framework.CrudDao;
import com.seezoon.dao.modules.sys.entity.SysDict;

/**
 * 字典
 * @author seezoon-generator 2020年12月2日 下午11:35:26
 */
@Repository
@Mapper
public interface SysDictDao extends CrudDao<SysDict, Integer> {

}