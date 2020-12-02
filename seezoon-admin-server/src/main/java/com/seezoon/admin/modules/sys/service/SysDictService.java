package com.seezoon.admin.modules.sys.service;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seezoon.admin.framework.service.AbstractCrudService;
import com.seezoon.dao.modules.sys.SysDictDao;
import com.seezoon.dao.modules.sys.entity.SysDict;
import com.seezoon.dao.modules.sys.entity.SysDictCondition;

/**
 * 数据字典
 *
 * @author seezoon-generator 2020年12月2日 下午11:35:26
 */
@Service
public class SysDictService extends AbstractCrudService<SysDictDao, SysDict, Integer> {

    @Cacheable(cacheNames = "SysDict", key = "#type")
    @Transactional(readOnly = true)
    public List<SysDict> find(@NotEmpty String type) {
        SysDictCondition sysDictCondition = new SysDictCondition();
        sysDictCondition.setType(type);
        List<SysDict> sysDicts = this.find(sysDictCondition);
        return sysDicts;
    }
}
