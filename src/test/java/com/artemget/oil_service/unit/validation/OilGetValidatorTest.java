package com.artemget.oil_service.unit.validation;

import com.artemget.oil_service.exception.ParameterValidationException;
import com.artemget.oil_service.validation.HttpValidator;
import com.artemget.oil_service.validation.OilGetValidator;
import io.vertx.ext.web.RoutingContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class OilGetValidatorTest {
    private final HttpValidator finderValidator = new OilGetValidator();
    private final RoutingContext event = mock(RoutingContext.class);

    @Test
    public void onSuccess() {
        when(event.pathParam("record")).thenReturn("123");
        assertDoesNotThrow(() -> finderValidator.validate(event));
        reset(event);
    }

    @Test
    public void failOnNull() {
        when(event.pathParam("record")).thenReturn(null);
        assertThrows(ParameterValidationException.class, () -> finderValidator.validate(event));
        reset(event);
    }

    @Test
    public void failOnNotLong() {
        when(event.pathParam("record")).thenReturn("not long");
        assertThrows(ParameterValidationException.class, () -> finderValidator.validate(event));
        reset(event);
    }
}
