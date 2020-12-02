package com.seezoon.admin.modules.sys.service;

import java.util.List;

import javax.validation.constraints.NotEmpty;

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
    public SysParam findByParamKey(@NotEmpty String paramKey) {
        SysParamCondition sysParamCondition = new SysParamCondition();
        sysParamCondition.setParamKey(paramKey);
        List<SysParam> sysParams = this.d.selectByCondition(sysParamCondition);
        return sysParams.isEmpty() ? null : sysParams.get(0);
    }
}
