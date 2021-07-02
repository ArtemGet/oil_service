package com.artemget.oil_service.validation;

import com.artemget.oil_service.exception.ParameterValidationException;
import io.vertx.ext.web.RoutingContext;

public interface HttpValidator {
    void setEvent(RoutingContext event);

    default void validate(RoutingContext event) {
        try {
            setEvent(event);
        } catch (Exception e) {
            throw new ParameterValidationException(e);
        }
    }
}
