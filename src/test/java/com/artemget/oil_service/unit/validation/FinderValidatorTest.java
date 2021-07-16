package com.artemget.oil_service.unit.validation;

import com.artemget.oil_service.exception.ParameterValidationException;
import com.artemget.oil_service.validation.FinderValidator;
import com.artemget.oil_service.validation.HttpValidator;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FinderValidatorTest {
    private final HttpValidator finderValidator = new FinderValidator();
    private final RoutingContext event = mock(RoutingContext.class);

    @Test
    public void onSuccess() {
        when(event.pathParam("param")).thenReturn("density20");
        when(event.getBodyAsJson()).thenReturn(new JsonObject().put("value", 0.123));
        when(event.pathParam("limit")).thenReturn("1");
        assertDoesNotThrow(() -> finderValidator.validate(event));
    }

    @Test
    public void failOnNullParams() {
        when(event.pathParam("param")).thenReturn(null);
        when(event.getBodyAsJson()).thenReturn(new JsonObject());
        when(event.pathParam("limit")).thenReturn(null);
        assertThrows(ParameterValidationException.class, () -> finderValidator.validate(event));
    }

    @Test
    public void failOnEmptyParams() {
        when(event.pathParam("param")).thenReturn("");
        when(event.getBodyAsJson()).thenReturn(new JsonObject());
        when(event.pathParam("limit")).thenReturn("");
        assertThrows(ParameterValidationException.class, () -> finderValidator.validate(event));
    }

    @Test
    public void failOnInvalidParamType() {
        when(event.pathParam("param")).thenReturn("density20");
        when(event.getBodyAsJson()).thenReturn(new JsonObject().put("value", "not double"));
        when(event.pathParam("limit")).thenReturn("not long");
        assertThrows(ParameterValidationException.class, () -> finderValidator.validate(event));
    }

    @Test
    public void failOnWrongParamPattern() {
        when(event.pathParam("param")).thenReturn("SELECT * FROM blabla WHERE id = 123");
        when(event.getBodyAsJson()).thenReturn(new JsonObject().put("value", 0.123));
        when(event.pathParam("limit")).thenReturn("1");
        assertThrows(ParameterValidationException.class, () -> finderValidator.validate(event));
    }

    @Test
    public void failOnWrongValues() {
        when(event.pathParam("param")).thenReturn("density20");
        when(event.getBodyAsJson()).thenReturn(new JsonObject().put("value", -0.123));
        when(event.pathParam("limit")).thenReturn("-1");
        assertThrows(ParameterValidationException.class, () -> finderValidator.validate(event));
    }
}
