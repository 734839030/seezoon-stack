package com.seezoon.admin.modules.sys.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.seezoon.admin.BaseSpringBootTest;

/**
 * @author hdf
 */
class SysUserRoleServiceTest extends BaseSpringBootTest {

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Test
    void roleAssign() {
        sysUserRoleService.assign(null);
    }
}