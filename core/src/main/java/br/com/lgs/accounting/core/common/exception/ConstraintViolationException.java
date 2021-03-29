package br.com.lgs.accounting.core.common.exception;

import br.com.lgs.accounting.core.common.validation.ConstraintViolation;

import java.util.Set;

public class ConstraintViolationException extends IllegalArgumentException {
    private Set<ConstraintViolation<Object>> violations;

    public ConstraintViolationException(Set<ConstraintViolation<Object>> violations) {
        this.violations = violations;
    }

    public Set<ConstraintViolation<Object>> getViolations() {
        return violations;
    }
}
