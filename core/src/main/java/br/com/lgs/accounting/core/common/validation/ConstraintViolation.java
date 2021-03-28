package br.com.lgs.accounting.core.common.validation;

public class ConstraintViolation<T> {
    private final String field;
    private final String message;

    public ConstraintViolation(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
