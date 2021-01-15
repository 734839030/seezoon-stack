package com.seezoon.framework.exception;

/**
 * 运行时候异常
 *
 * @author hdf
 */
public class ServerRuntimeException extends RuntimeException {

    public ServerRuntimeException(Throwable throwable) {
        super(throwable);
    }
}
