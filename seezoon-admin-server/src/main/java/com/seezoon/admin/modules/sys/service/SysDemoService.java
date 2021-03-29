package com.seezoon.admin.modules.sys.service;

import javax.validation.constraints.NotBlank;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seezoon.admin.framework.service.AbstractCrudService;
import com.seezoon.dao.modules.sys.SysDemoDao;
import com.seezoon.dao.modules.sys.entity.SysDemo;
import com.seezoon.dao.modules.sys.entity.SysDemoCondition;

/**
 * 生成案例
 *
 * @author seezoon-generator 2021年3月16日 上午1:16:00
 */
@Service
public class SysDemoService extends AbstractCrudService<SysDemoDao, SysDemo, Integer> {

    @Transactional(readOnly = true)
    public SysDemo findByInputText(@NotBlank String inputText) {
        SysDemoCondition sysDemoCondition = new SysDemoCondition();
        sysDemoCondition.setInputText(inputText);
        return this.findOne(sysDemoCondition);
    }
}
