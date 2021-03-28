package br.com.lgs.accounting.core.common.exception;

import br.com.lgs.accounting.core.common.validation.ConstraintViolation;

import java.util.Set;

public class ConstraintViolationException extends IllegalArgumentException {
    private Set<ConstraintViolation<Object>> violations;

    public ConstraintViolationException(Set<ConstraintViolation<Object>> violations) {
        this.violations = violations;
    }

    public ConstraintViolationException(String s, Set<ConstraintViolation<Object>> violations) {
        super(s);
        this.violations = violations;
    }

    public ConstraintViolationException(String message, Throwable cause, Set<ConstraintViolation<Object>> violations) {
        super(message, cause);
        this.violations = violations;
    }

    public ConstraintViolationException(Throwable cause, Set<ConstraintViolation<Object>> violations) {
        super(cause);
        this.violations = violations;
    }
}
