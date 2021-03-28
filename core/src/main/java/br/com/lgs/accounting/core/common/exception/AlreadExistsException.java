package br.com.lgs.accounting.core.common.exception;

public class AlreadExistsException extends BaseRuntimeException{
    public AlreadExistsException(String daf) {
    }

    @Override
    public String getExceptionKey() {
        return null;
    }
}
