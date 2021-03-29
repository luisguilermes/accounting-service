package br.com.lgs.accounting.config;

import br.com.lgs.accounting.core.common.exception.*;
import br.com.lgs.accounting.payload.ErrorMessageResponse;
import br.com.lgs.accounting.payload.FIeldsErrorsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler {

    @ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(value = {AlreadExistsException.class, BusinessRuleException.class})
    public final ResponseEntity<ErrorMessageResponse> handleAlreadExistsException(BaseRuntimeException exception) {
        return ResponseEntity.unprocessableEntity().body(new ErrorMessageResponse(
                LocalDateTime.now(),
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Ocorreu algum erro de negócio.",
                exception.getMessage()
        ));
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<ErrorMessageResponse> handleConstraintNotFound(NotFoundException ex) {
        return ResponseEntity.notFound().build();
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<ErrorMessageResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        return ResponseEntity.badRequest().body(new ErrorMessageResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Requisição não pode ser processada.",
                ex.getViolations().stream().map(item -> new FIeldsErrorsResponse(item.getField(), item.getMessage()))
                        .collect(Collectors.toList()))
        );
    }
}
