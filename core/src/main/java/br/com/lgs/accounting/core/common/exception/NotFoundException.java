package br.com.lgs.accounting.core.common.exception;

public class NotFoundException extends BaseRuntimeException{

    public NotFoundException(String message) {
    }

    @Override
    public String getExceptionKey() {
        return null;
    }
}
