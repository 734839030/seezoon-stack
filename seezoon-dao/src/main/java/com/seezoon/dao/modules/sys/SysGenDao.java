package com.seezoon.dao.modules.sys;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.seezoon.dao.framework.CrudDao;
import com.seezoon.dao.modules.sys.entity.SysGen;

/**
 * 代码生成
 * @author seezoon-generator 2021年3月29日 下午11:27:05
 */
@Repository
@Mapper
public interface SysGenDao extends CrudDao<SysGen, Integer> {

}