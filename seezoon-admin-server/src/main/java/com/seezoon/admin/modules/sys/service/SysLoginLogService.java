package com.seezoon.admin.modules.sys.service;

import javax.validation.constraints.NotEmpty;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.seezoon.admin.framework.service.AbstractCrudService;
import com.seezoon.dao.modules.sys.SysLoginLogDao;
import com.seezoon.dao.modules.sys.entity.SysLoginLog;

/**
 * 登录日志
 *
 * @author seezoon-generator 2021年4月18日 上午1:30:18
 */
@Service
public class SysLoginLogService extends AbstractCrudService<SysLoginLogDao, SysLoginLog, Integer> {

    @Async
    @Override
    public int save(@NotEmpty SysLoginLog... records) {
        return super.save(records);
    }
}
