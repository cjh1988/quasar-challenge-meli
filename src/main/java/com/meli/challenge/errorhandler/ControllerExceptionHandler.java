package com.meli.challenge.errorhandler;

import com.meli.challenge.dto.ErrorDTO;
import com.meli.challenge.exception.FieldValidatorException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(FieldValidatorException.class)
    protected ResponseEntity<Object> handleFieldValidatorException(FieldValidatorException ex) {
        var errorDto = ErrorDTO.builder()
                .code(String.valueOf(ex.getHttpStatus().value()))
                .description(ex.getMessage())
                .build();
        return ResponseEntity.status(ex.getHttpStatus()).body(errorDto);
    }
}
