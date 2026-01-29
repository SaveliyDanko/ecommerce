package com.savadanko.ecommerce.exceptions;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        Map<String, String> responses = new HashMap<>();

        e.getBindingResult().getAllErrors().forEach(err -> {
            String fieldName = ((FieldError) err).getField();
            String message = err.getDefaultMessage();
            responses.put(fieldName, message);
        });

        return responses;
    }
}
