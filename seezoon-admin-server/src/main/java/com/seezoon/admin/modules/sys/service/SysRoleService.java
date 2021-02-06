package com.seezoon.admin.modules.sys.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.seezoon.admin.framework.service.AbstractCrudService;
import com.seezoon.admin.modules.sys.dto.RoleAssignAo;
import com.seezoon.dao.modules.sys.SysRoleDao;
import com.seezoon.dao.modules.sys.SysRoleMenuDao;
import com.seezoon.dao.modules.sys.entity.SysRole;
import com.seezoon.dao.modules.sys.entity.SysRoleCondition;
import com.seezoon.dao.modules.sys.entity.SysRoleMenu;

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
    private final SysRoleMenuDao sysRoleMenuDao;

    @Override
    public SysRole find(@NotNull Integer integer) {
        SysRole sysRole = super.find(integer);
        if (null != sysRole) {
            // 放入菜单
            sysRole.setMenuIds(sysRoleMenuDao.selectMenuIdsByRoleId(sysRole.getId()));
        }
        return sysRole;
    }

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

    public int save(@NotNull SysRole record) {
        int count = super.save(record);
        saveRoleMenus(record.getMenuIds(), record.getId());
        return count;
    }

    @Override
    public int updateSelective(@NotNull SysRole record) {
        sysRoleMenuDao.deleteByRole(record.getId());
        this.saveRoleMenus(record.getMenuIds(), record.getId());
        return super.updateSelective(record);
    }

    private void saveRoleMenus(List<Integer> menuIds, Integer roleId) {
        if (!CollectionUtils.isEmpty(menuIds)) {
            List<SysRoleMenu> sysRoleMenus =
                menuIds.stream().map(menuId -> new SysRoleMenu(roleId, menuId)).collect(Collectors.toList());
            int saveRoleMenuCount = sysRoleMenuDao.insert(sysRoleMenus.toArray(SysRoleMenu[]::new));
            logger.debug("saved {} sys_role_menu roleId={}", roleId, saveRoleMenuCount);
        }
    }

    public int delete(@NotNull Integer roleId) {
        int count = sysRoleMenuDao.deleteByRole(roleId);
        logger.debug("deleted {} sys_role_menu roleId={}", count, roleId);
        return super.delete(roleId);
    }

    public int assign(@Valid @NotNull RoleAssignAo roleAssignAo) {
        return this.sysUserRoleService.assign(roleAssignAo);
    }
}
