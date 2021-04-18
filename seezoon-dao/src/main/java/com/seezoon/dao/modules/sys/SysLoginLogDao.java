package com.seezoon.dao.modules.sys;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.seezoon.dao.framework.CrudDao;
import com.seezoon.dao.modules.sys.entity.SysLoginLog;

/**
 * 登录日志
 * @author seezoon-generator 2021年4月18日 上午1:30:18
 */
@Repository
@Mapper
public interface SysLoginLogDao extends CrudDao<SysLoginLog, Integer> {

}