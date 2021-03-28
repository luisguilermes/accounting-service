package br.com.lgs.accounting.core.common.validation;


import br.com.lgs.accounting.core.common.exception.ConstraintViolationException;

import java.util.Set;

public class SelfValidating<T> {
    protected void validateSelf() {
        Set<ConstraintViolation<Object>> violations = Validator.validate(this);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
