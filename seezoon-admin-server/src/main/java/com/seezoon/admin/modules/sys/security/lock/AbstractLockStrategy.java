package com.seezoon.admin.modules.sys.security.lock;

import java.time.Duration;

import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.Assert;

import lombok.RequiredArgsConstructor;

/**
 * @author hdf
 */
@RequiredArgsConstructor
public abstract class AbstractLockStrategy implements LockStrategy {

    private final Duration lockTime;
    private final int lockFailTimes;
    private final ValueOperations<String, Integer> valueOperations;

    @Override
    public long increment(String key) {
        checkKey(key);
        String wrappedKey = wrapKey(key);
        if (!valueOperations.getOperations().hasKey(wrappedKey)) {
            valueOperations.set(wrappedKey, 1, lockTime);
            return 1;
        } else {
            Long increment = valueOperations.increment(wrappedKey);
            valueOperations.getOperations().expire(wrappedKey, lockTime);
            return increment;
        }
    }

    private void checkKey(String key) {
        Assert.hasText(key, "key must has text");
    }

    @Override
    public boolean isLocked(String key) {
        checkKey(key);
        String wrappedKey = wrapKey(key);
        Integer failCnt = valueOperations.get(wrappedKey);
        if (null != failCnt && failCnt >= lockFailTimes) {
            return true;
        }
        return false;
    }

    @Override
    public void clear(String key) {
        checkKey(key);
        String wrappedKey = wrapKey(key);
        valueOperations.getOperations().delete(wrappedKey);
    }

    protected abstract String wrapKey(String key);
}
