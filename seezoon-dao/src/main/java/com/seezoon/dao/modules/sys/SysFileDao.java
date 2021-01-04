package com.seezoon.dao.modules.sys;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.seezoon.dao.framework.CrudDao;
import com.seezoon.dao.modules.sys.entity.SysFile;

/**
 * 文件
 * @author seezoon-generator 2021年1月2日 上午1:04:41
 */
@Repository
@Mapper
public interface SysFileDao extends CrudDao<SysFile, Integer> {

}