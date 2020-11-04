package com.seezoon.dao.framework.constants;

/**
 * @author hdf
 */
public enum EntityStatus {
    /**
     * DB 字段默认状态
     */
    NORMAL((byte)1), INVALID((byte)0);

    private byte status;

    EntityStatus(byte status) {
        this.status = status;
    }

    public byte status() {
        return status;
    }
}
