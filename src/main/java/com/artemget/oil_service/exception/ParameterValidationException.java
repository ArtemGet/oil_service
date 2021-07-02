package com.artemget.oil_service.exception;

public class ParameterValidationException extends RuntimeException {
    public ParameterValidationException(Exception e) {
        super(e);
    }
}
