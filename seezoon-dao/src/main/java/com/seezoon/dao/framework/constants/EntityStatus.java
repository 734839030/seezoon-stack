package com.seezoon.dao.framework.constants;

/**
 * @author hdf
 */
public enum EntityStatus {
    /**
     * DB 字段默认状态
     */
    NORMAL(1), INVALID(0);

    private int status;

    EntityStatus(int status) {
        this.status = status;
    }

    public int status() {
        return status;
    }
}
