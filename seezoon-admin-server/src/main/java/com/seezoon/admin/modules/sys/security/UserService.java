package com.seezoon.admin.modules.sys.security;

import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seezoon.admin.framework.service.AbstractTransactionService;
import com.seezoon.admin.modules.sys.dto.UserInfoVo;
import com.seezoon.admin.modules.sys.service.SysMenuService;
import com.seezoon.admin.modules.sys.service.SysRoleService;
import com.seezoon.dao.framework.constants.EntityStatus;
import com.seezoon.dao.modules.sys.entity.SysMenu;
import com.seezoon.dao.modules.sys.entity.SysMenuCondition;
import com.seezoon.dao.modules.sys.entity.SysRole;
import com.seezoon.dao.modules.sys.entity.SysRoleCondition;

import lombok.RequiredArgsConstructor;

/**
 * @author hdf
 */
@RequiredArgsConstructor
@Service
public class UserService extends AbstractTransactionService {

    private final SysRoleService sysRoleService;
    private final SysMenuService sysMenuService;

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
    public UserInfoVo userInfo(@NotNull Integer userId) {
        UserInfoVo userInfo = null;

        return userInfo;
    }
}
