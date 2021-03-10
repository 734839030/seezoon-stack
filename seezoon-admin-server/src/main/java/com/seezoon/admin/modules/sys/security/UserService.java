package com.seezoon.admin.modules.sys.security;

import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.seezoon.admin.framework.file.FileService;
import com.seezoon.admin.framework.service.AbstractTransactionService;
import com.seezoon.admin.modules.sys.dto.UserAo;
import com.seezoon.admin.modules.sys.dto.UserInfo;
import com.seezoon.admin.modules.sys.service.SysMenuService;
import com.seezoon.admin.modules.sys.service.SysRoleService;
import com.seezoon.admin.modules.sys.service.SysUserService;
import com.seezoon.dao.framework.constants.EntityStatus;
import com.seezoon.dao.modules.sys.entity.*;

import lombok.RequiredArgsConstructor;

/**
 * @author hdf
 */
@RequiredArgsConstructor
@Service
public class UserService extends AbstractTransactionService {

    private final SysRoleService sysRoleService;
    private final SysMenuService sysMenuService;
    private final SysUserService sysUserService;
    private final FileService fileService;

    @Transactional(readOnly = true)
    public List<SysRole> findRolesByUserId(@NotNull Integer userId) {
        if (Objects.equals(SecurityUtils.SUPER_ADMIN_USER_ID, userId)) {
            SysRoleCondition sysRoleCondition = new SysRoleCondition();
            sysRoleCondition.setStatus(EntityStatus.NORMAL.status());
            return sysRoleService.find(sysRoleCondition);
        }
        return sysRoleService.findByUserId(userId);
    }

    @Transactional(readOnly = true)
    public List<SysMenu> findMenusByUserId(@NotNull Integer userId) {
        if (Objects.equals(SecurityUtils.SUPER_ADMIN_USER_ID, userId)) {
            SysMenuCondition sysMenuCondition = new SysMenuCondition();
            sysMenuCondition.setStatus(EntityStatus.NORMAL.status());
            return sysMenuService.find(sysMenuCondition);
        }
        return sysMenuService.findByUserId(userId);
    }

    @Transactional(readOnly = true)
    public UserInfo userInfo(@NotNull Integer userId) {
        SysUser sysUser = sysUserService.find(userId);
        Assert.notNull(sysUser, "用户不存在");
        UserInfo userInfo =
            new UserInfo(sysUser.getId(), sysUser.getDeptId(), sysUser.getUsername(), sysUser.getName());
        userInfo.setDeptName(sysUser.getDeptName());
        userInfo.setPhotoUrl(fileService.getUrl(sysUser.getPhoto()));
        userInfo.setPhoto(sysUser.getPhoto());
        userInfo.setMobile(sysUser.getMobile());
        userInfo.setEmail(sysUser.getEmail());
        return userInfo;
    }

    public int saveUserInfo(@NotNull Integer userId, @NotNull UserAo userAo) {
        SysUser sysUser = this.sysUserService.find(userId);
        Assert.notNull(sysUser, "user must not null");
        sysUser.setName(userAo.getName());
        sysUser.setEmail(userAo.getEmail());
        sysUser.setPhoto(userAo.getPhoto());
        return this.sysUserService.update(sysUser);
    }

    public boolean updatePassword(@NotNull Integer userId, @NotEmpty String oldPassword, @NotEmpty String newPassword) {
        SysUser sysUser = this.sysUserService.find(userId);
        Assert.notNull(sysUser, "user must not null");
        boolean matches = AdminPasswordEncoder.matches(oldPassword, sysUser.getPassword());
        if (matches) {
            sysUser.setPassword(AdminPasswordEncoder.encode(newPassword));
            this.sysUserService.update(sysUser);
            return true;
        }
        return false;
    }
}
