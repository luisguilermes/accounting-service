package br.com.lgs.accounting.core.common.exception;

public class BusinessRuleException extends BaseRuntimeException{
    public BusinessRuleException(String daf) {
    }

    @Override
    public String getExceptionKey() {
        return null;
    }
}
