package com.seezoon.modules.sys;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.seezoon.dao.framework.CrudDao;
import com.seezoon.modules.sys.entity.SysParam;

/**
 * @author hdf
 */
@Repository
@Mapper
public interface SysParamDao extends CrudDao<SysParam, Long> {

}