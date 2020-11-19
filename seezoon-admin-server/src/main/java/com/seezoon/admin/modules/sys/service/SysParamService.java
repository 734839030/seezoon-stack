package com.seezoon.admin.modules.sys.service;

import org.springframework.stereotype.Service;

import com.seezoon.admin.framework.service.AbstractCrudService;
import com.seezoon.dao.modules.sys.SysParamDao;
import com.seezoon.dao.modules.sys.entity.SysParam;

/**
 * @author hdf
 */
@Service
public class SysParamService extends AbstractCrudService<SysParamDao, SysParam, Integer> {

}
