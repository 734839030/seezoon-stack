package com.seezoon.admin.modules.sys.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seezoon.admin.framework.service.AbstractCrudService;
import com.seezoon.admin.modules.sys.dto.RoleAssignAo;
import com.seezoon.dao.modules.sys.SysRoleDao;
import com.seezoon.dao.modules.sys.entity.SysRole;
import com.seezoon.dao.modules.sys.entity.SysRoleCondition;

import lombok.RequiredArgsConstructor;

/**
 * 角色表
 *
 * @author seezoon-generator 2021年1月25日 上午12:14:26
 */
@RequiredArgsConstructor
@Service
public class SysRoleService extends AbstractCrudService<SysRoleDao, SysRole, Integer> {

    private final SysUserRoleService sysUserRoleService;

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

    public int delete(@NotNull Integer roleId) {
        // int cnt = sysUserRoleService.deleteByRoleId(roleId);
        // logger.debug("delete user role cnt={}", cnt);
        return super.delete(roleId);
    }

    public int assign(@Valid @NotNull RoleAssignAo roleAssignAo) {
        return this.sysUserRoleService.assign(roleAssignAo);
    }
}
