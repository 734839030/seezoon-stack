package com.seezoon.admin.modules.sys.service;

import java.util.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seezoon.admin.framework.service.AbstractCrudService;
import com.seezoon.admin.modules.sys.dto.RoleAssignAo;
import com.seezoon.dao.framework.constants.EntityStatus;
import com.seezoon.dao.modules.sys.SysUserRoleDao;
import com.seezoon.dao.modules.sys.entity.SysUserRole;
import com.seezoon.dao.modules.sys.entity.SysUserRoleCondition;

/**
 * 用户-角色
 *
 * @author seezoon-generator 2021年1月26日 下午11:14:43
 */
@Service
public class SysUserRoleService extends AbstractCrudService<SysUserRoleDao, SysUserRole, Integer> {

    /**
     * 更新用户角色
     * <p>
     * 为了自动记录创建人和修改人信息，采用编码方式实现
     *
     * @param userId
     * @param roleIds
     * @return
     */
    public int saveOrUpdateUserRoles(@NotNull Integer userId, List<Integer> roleIds) {
        if (roleIds == null) {
            roleIds = Collections.emptyList();
        }
        // 用户所有的角色
        List<SysUserRole> sysUserRoles = this.find(new Integer[] {userId}, null, null);
        Set<Integer> handledRoleIds = new HashSet<>();
        for (SysUserRole sysUserRole : sysUserRoles) {
            if (roleIds.contains(sysUserRole.getRoleId())) {
                handledRoleIds.add(sysUserRole.getRoleId());
                if (Objects.equals(EntityStatus.NORMAL.status(), sysUserRole.getStatus())) {
                    continue;
                }
                sysUserRole.setStatus(EntityStatus.NORMAL.status());
            } else {
                if (Objects.equals(EntityStatus.INVALID.status(), sysUserRole.getStatus())) {
                    continue;
                }
                sysUserRole.setStatus(EntityStatus.INVALID.status());
            }
            this.updateSelective(sysUserRole);
        }
        List<SysUserRole> toSaveUserRoles = new ArrayList<>();
        for (Integer roleId : roleIds) {
            if (!handledRoleIds.contains(roleId)) {
                SysUserRole toSave = new SysUserRole();
                toSave.setUserId(userId);
                toSave.setRoleId(roleId);
                toSave.setStatus(EntityStatus.NORMAL.status());
                toSaveUserRoles.add(toSave);
            }
        }
        if (!toSaveUserRoles.isEmpty()) {
            this.save(toSaveUserRoles.toArray(SysUserRole[]::new));
        }
        return roleIds.size();
    }

    @Transactional(readOnly = true)
    public List<SysUserRole> findValid(@NotNull Integer userId) {
        return this.find(new Integer[] {userId}, EntityStatus.NORMAL.status(), null);
    }

    @Transactional(readOnly = true)
    public List<SysUserRole> find(@NotEmpty Integer[] userIds, Integer status, Integer roleId) {
        SysUserRoleCondition sysUserRoleCondition = new SysUserRoleCondition();
        sysUserRoleCondition.setUserIds(userIds);
        sysUserRoleCondition.setStatus(status);
        sysUserRoleCondition.setRoleId(roleId);
        return this.find(sysUserRoleCondition);
    }

    public int deleteByRoleId(@NotNull Integer roleId) {
        return this.d.deleteByRoleId(roleId);
    }

    public int assign(@NotNull @Valid RoleAssignAo roleAssignAo) {
        // 分配角色
        if (roleAssignAo.getAddUser()) {
            List<SysUserRole> sysUserRoles = this.find(roleAssignAo.getUserIds(), null, roleAssignAo.getRoleId());
            List<Integer> handledUserIds = new ArrayList<>();
            for (SysUserRole sysUserRole : sysUserRoles) {
                handledUserIds.add(sysUserRole.getUserId());
                // 已删除则恢复
                if (Objects.equals(sysUserRole.getStatus(), EntityStatus.INVALID.status())) {
                    sysUserRole.setStatus(EntityStatus.NORMAL.status());
                    this.updateSelective(sysUserRole);
                } else {
                    continue;
                }
            }
            List<SysUserRole> toSaveUserRoles = new ArrayList<>();
            for (Integer userId : roleAssignAo.getUserIds()) {
                if (!handledUserIds.contains(userId)) {
                    SysUserRole toSave = new SysUserRole();
                    toSave.setUserId(userId);
                    toSave.setRoleId(roleAssignAo.getRoleId());
                    toSave.setStatus(EntityStatus.NORMAL.status());
                    toSaveUserRoles.add(toSave);
                }
            }
            if (!toSaveUserRoles.isEmpty()) {
                this.save(toSaveUserRoles.toArray(SysUserRole[]::new));
            }
        } else { // 移除
            List<SysUserRole> sysUserRoles =
                this.find(roleAssignAo.getUserIds(), EntityStatus.NORMAL.status(), roleAssignAo.getRoleId());
            for (SysUserRole sysUserRole : sysUserRoles) {
                sysUserRole.setStatus(EntityStatus.INVALID.status());
                this.updateSelective(sysUserRole);
            }
        }
        return roleAssignAo.getUserIds().length;
    }

}
