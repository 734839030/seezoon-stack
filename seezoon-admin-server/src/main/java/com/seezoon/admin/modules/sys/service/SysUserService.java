package com.seezoon.admin.modules.sys.service;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seezoon.admin.framework.service.AbstractCrudService;
import com.seezoon.dao.modules.sys.SysUserDao;
import com.seezoon.dao.modules.sys.entity.SysUser;
import com.seezoon.dao.modules.sys.entity.SysUserCondition;

/**
 * 用户信息
 *
 * @author seezoon-generator 2021年1月16日 下午11:55:54
 */
@Service
public class SysUserService extends AbstractCrudService<SysUserDao, SysUser, Integer> {

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
        if (StringUtils.isNotBlank(record.getPassword())) {
            record.setPassword(new BCryptPasswordEncoder().encode(record.getPassword()));
        } else {
            record.setPassword(null);
        }
        return super.updateSelective(record);
    }

    public int save(@NotNull SysUser record) {
        if (StringUtils.isNotBlank(record.getPassword())) {
            record.setPassword(new BCryptPasswordEncoder().encode(record.getPassword()));
        } else {
            record.setPassword(null);
        }
        return super.save(record);
    }
}
