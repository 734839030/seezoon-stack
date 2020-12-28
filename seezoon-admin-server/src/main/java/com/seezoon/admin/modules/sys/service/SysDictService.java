package com.seezoon.admin.modules.sys.service;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seezoon.admin.framework.service.AbstractCrudService;
import com.seezoon.dao.modules.sys.SysDictDao;
import com.seezoon.dao.modules.sys.entity.SysDict;
import com.seezoon.dao.modules.sys.entity.SysDictCondition;

/**
 * 字典
 *
 * @author seezoon-generator 2020年12月26日 上午1:50:03
 */
@Service
public class SysDictService extends AbstractCrudService<SysDictDao, SysDict, Integer> {

    @Transactional(readOnly = true)
    public SysDict findByTypeAndCode(@NotBlank String type, @NotBlank String code) {
        SysDictCondition condition = new SysDictCondition();
        condition.setType(type);
        condition.setCode(code);
        return this.findOne(condition);
    }

    @Transactional(readOnly = true)
    public List<String> findTypes() {
        return this.d.selectTypes();
    }
}
