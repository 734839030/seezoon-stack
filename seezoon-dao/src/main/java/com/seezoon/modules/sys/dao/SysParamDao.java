package com.seezoon.modules.sys.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.seezoon.framework.dao.CrudDao;
import com.seezoon.modules.sys.entity.SysParam;

/**
 * @author hdf
 */
@Repository
@Mapper
public interface SysParamDao extends CrudDao<SysParam, Long> {

}