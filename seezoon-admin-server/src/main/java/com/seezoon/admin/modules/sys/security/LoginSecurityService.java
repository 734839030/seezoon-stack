package com.seezoon.admin.modules.sys.security;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.seezoon.admin.framework.properties.SeezoonAdminProperties;
import com.seezoon.admin.framework.service.AbstractBaseService;
import com.seezoon.admin.modules.sys.security.lock.IpLockStrategy;
import com.seezoon.admin.modules.sys.security.lock.LockStrategy;
import com.seezoon.admin.modules.sys.security.lock.UsernameLockStrategy;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author hdf
 */
@Service
@RequiredArgsConstructor
public class LoginSecurityService extends AbstractBaseService implements InitializingBean {

    private final SeezoonAdminProperties seezoonAdminProperties;
    private SeezoonAdminProperties.LoginProperties loginProperties;
    @Resource(name = "redisTemplate")
    private ValueOperations<String, Integer> valueOperations;
    private LockStrategy usernameLockStrategy;
    private LockStrategy ipLockStrategy;

    @Override
    public void afterPropertiesSet() throws Exception {
        loginProperties = seezoonAdminProperties.getLogin();
        usernameLockStrategy = new UsernameLockStrategy(loginProperties.getLockTime(),
            loginProperties.getLockPasswdFailTimes(), valueOperations);
        ipLockStrategy =
            new IpLockStrategy(loginProperties.getLockTime(), loginProperties.getLockIpFailTimes(), valueOperations);
    }

    public void clear(String username, String ip) {
        if (StringUtils.isNotBlank(username)) {
            usernameLockStrategy.clear(username);
        }
        if (StringUtils.isNotBlank(ip)) {
            ipLockStrategy.clear(ip);
        }
    }

    public LockStrategy getUsernameLockStrategy() {
        return usernameLockStrategy;
    }

    public LockStrategy getIpLockStrategy() {
        return ipLockStrategy;
    }
}
