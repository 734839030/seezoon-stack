package com.seezoon.admin.modules.sys.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.seezoon.admin.framework.service.AbstractCrudService;
import com.seezoon.dao.modules.sys.SysGenDao;
import com.seezoon.dao.modules.sys.entity.SysGen;
import com.seezoon.generator.plan.UserTablePlanParam;

import lombok.RequiredArgsConstructor;

/**
 * 代码生成
 *
 * @author seezoon-generator 2021年3月29日 下午11:27:05
 */
@Service
@RequiredArgsConstructor
public class SysGenService extends AbstractCrudService<SysGenDao, SysGen, Integer> {

    /**
     * 保存和修改方案
     *
     * @param id
     * @param userTablePlanParam
     * @return
     */
    public int save(Integer id, @NotNull @Valid UserTablePlanParam userTablePlanParam) {
        SysGen sysGen = new SysGen();
        sysGen.setId(id);
        sysGen.setTableName(userTablePlanParam.getTableName());
        sysGen.setMenuName(userTablePlanParam.getMenuName());
        sysGen.setModuleName(userTablePlanParam.getModuleName());
        sysGen.setFunctionName(userTablePlanParam.getFunctionName());
        sysGen.setTemplate(userTablePlanParam.getTemplateType());
        sysGen.setClassName(userTablePlanParam.getClassName());
        sysGen.setColumns(JSON.toJSONString(userTablePlanParam.getColumnPlans()));
        return id == null ? this.save(sysGen) : this.update(sysGen);
    }
}
