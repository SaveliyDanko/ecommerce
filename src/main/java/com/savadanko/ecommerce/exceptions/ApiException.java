package com.savadanko.ecommerce.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ApiException extends RuntimeException {
    private static final Long serialVersionUID = 1L;

    public ApiException(String message) {
        super(message);
    }
}
