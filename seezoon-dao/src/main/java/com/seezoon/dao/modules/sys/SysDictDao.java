package com.seezoon.dao.modules.sys;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.seezoon.dao.framework.CrudDao;
import com.seezoon.dao.modules.sys.entity.SysDict;

/**
 * 字典
 *
 * @author seezoon-generator 2020年12月26日 上午1:50:03
 */
@Repository
@Mapper
public interface SysDictDao extends CrudDao<SysDict, Integer> {

    public List<String> selectTypes();
}