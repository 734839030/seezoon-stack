package com.seezoon.admin.modules.sys.security.lock;

/**
 * @author hdf
 */
public interface LockStrategy {
    long increment(String key);

    boolean isLocked(String key);

    void clear(String key);
}
