package com.seezoon.admin.modules.sys.service;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seezoon.admin.framework.service.AbstractCrudService;
import com.seezoon.dao.modules.sys.SysUserDao;
import com.seezoon.dao.modules.sys.entity.SysUser;
import com.seezoon.dao.modules.sys.entity.SysUserCondition;

import lombok.RequiredArgsConstructor;

/**
 * 用户信息
 *
 * @author seezoon-generator 2021年1月16日 下午11:55:54
 */
@Service
@RequiredArgsConstructor
public class SysUserService extends AbstractCrudService<SysUserDao, SysUser, Integer> {

    private final SysUserRoleService sysUserRoleService;

    @Transactional(readOnly = true)
    public SysUser findByUsername(@NotEmpty String username) {
        SysUserCondition sysUserCondition = new SysUserCondition();
        sysUserCondition.setUsername(username);
        return this.findOne(sysUserCondition);
    }

    @Transactional(readOnly = true)
    public SysUser findByMobile(@NotEmpty String mobile) {
        SysUserCondition sysUserCondition = new SysUserCondition();
        sysUserCondition.setMobile(mobile);
        return this.findOne(sysUserCondition);
    }

    @Override
    public int updateSelective(@NotNull SysUser record) {
        int cnt = super.updateSelective(record);
        if (cnt > 0) {
            sysUserRoleService.saveOrUpdateUserRoles(record.getId(), record.getRoleIds());
        }
        return cnt;

    }

    public int save(@NotNull SysUser record) {
        int cnt = super.save(record);
        if (cnt > 0) {
            sysUserRoleService.saveOrUpdateUserRoles(record.getId(), record.getRoleIds());
        }
        return cnt;
    }
}
