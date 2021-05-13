package com.seezoon.admin.modules.sys.security.data;

import com.seezoon.admin.modules.sys.security.SecurityUtils;
import com.seezoon.dao.framework.authority.DataAuthority;

/**
 * security 线程上下文中获取数据权限SQL
 * 
 * 不涉及系统模块
 */
public class DataAuthorityImpl implements DataAuthority {

    @Override
    public String getDsf() {
        return SecurityUtils.getDsf();
    }
}
