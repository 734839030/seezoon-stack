package com.seezoon.admin.modules.sys.service;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seezoon.admin.framework.service.AbstractCrudService;
import com.seezoon.dao.modules.sys.SysRoleDao;
import com.seezoon.dao.modules.sys.entity.SysRole;
import com.seezoon.dao.modules.sys.entity.SysRoleCondition;

/**
 * 角色表
 *
 * @author seezoon-generator 2021年1月25日 上午12:14:26
 */
@Service
public class SysRoleService extends AbstractCrudService<SysRoleDao, SysRole, Integer> {

    @Transactional(readOnly = true)
    public SysRole findByCode(@NotBlank String code) {
        SysRoleCondition sysRoleCondition = new SysRoleCondition();
        sysRoleCondition.setCode(code);
        return this.findOne(sysRoleCondition);
    }

    @Transactional(readOnly = true)
    public SysRole findByName(@NotBlank String name) {
        SysRoleCondition sysRoleCondition = new SysRoleCondition();
        sysRoleCondition.setName(name);
        return this.findOne(sysRoleCondition);
    }

    @Transactional(readOnly = true)
    public List<SysRole> findAll() {
        return this.find(new SysRoleCondition());
    }
}
