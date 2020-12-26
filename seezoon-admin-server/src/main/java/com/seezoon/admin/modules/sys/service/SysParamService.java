package com.seezoon.admin.modules.sys.service;

import javax.validation.constraints.NotBlank;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seezoon.admin.framework.service.AbstractCrudService;
import com.seezoon.dao.modules.sys.SysParamDao;
import com.seezoon.dao.modules.sys.entity.SysParam;
import com.seezoon.dao.modules.sys.entity.SysParamCondition;

/**
 * @author hdf
 */
@Service
public class SysParamService extends AbstractCrudService<SysParamDao, SysParam, Integer> {

    @Cacheable(cacheNames = "SysParam", key = "#paramKey")
    @Transactional(readOnly = true)
    public SysParam findCacheByParamKey(@NotBlank String paramKey) {
        return this.findByParamKey(paramKey);
    }

    @Transactional(readOnly = true)
    public SysParam findByParamKey(@NotBlank String paramKey) {
        SysParamCondition sysParamCondition = new SysParamCondition();
        sysParamCondition.setParamKey(paramKey);
        return this.findOne(sysParamCondition);
    }

}
