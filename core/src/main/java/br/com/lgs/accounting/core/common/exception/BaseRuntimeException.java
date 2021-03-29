package br.com.lgs.accounting.core.common.exception;

public abstract class BaseRuntimeException extends RuntimeException {
    public BaseRuntimeException(String message) {
        super(message);
    }
}
