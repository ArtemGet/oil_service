package com.artemget.oil_service.unit.validation;

import com.artemget.oil_service.exception.ParameterValidationException;
import com.artemget.oil_service.validation.FinderValidator;
import com.artemget.oil_service.validation.HttpValidator;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.RoutingContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FinderValidatorTest {
    private final HttpValidator finderValidator = new FinderValidator();
    private final RoutingContext event = mock(RoutingContext.class);
    private final HttpServerRequest request = mock(HttpServerRequest.class);

    @Test
    public void onSuccess() {
        when(event.request()).thenReturn(request);
        when(request.getParam("param")).thenReturn("density20");
        when(request.getParam("value")).thenReturn("0.123");
        when(request.getParam("limit")).thenReturn("1");
        assertDoesNotThrow(() -> finderValidator.validate(event));
    }

    @Test
    public void failOnNullParams() {
        when(event.request()).thenReturn(request);
        when(request.getParam("param")).thenReturn(null);
        when(request.getParam("value")).thenReturn(null);
        when(request.getParam("limit")).thenReturn(null);
        assertThrows(ParameterValidationException.class, () -> finderValidator.validate(event));
    }

    @Test
    public void failOnEmptyParams() {
        when(event.request()).thenReturn(request);
        when(request.getParam("param")).thenReturn("");
        when(request.getParam("value")).thenReturn("");
        when(request.getParam("limit")).thenReturn("");
        assertThrows(ParameterValidationException.class, () -> finderValidator.validate(event));
    }

    @Test
    public void failOnInvalidParamType() {
        when(event.request()).thenReturn(request);
        when(request.getParam("param")).thenReturn("density20");
        when(request.getParam("value")).thenReturn("not double");
        when(request.getParam("limit")).thenReturn("not long");
        assertThrows(ParameterValidationException.class, () -> finderValidator.validate(event));
    }

    @Test
    public void failOnWrongParamPattern() {
        when(event.request()).thenReturn(request);
        when(request.getParam("param")).thenReturn("SELECT * FROM bla WHERE id = 123");
        when(request.getParam("value")).thenReturn("0.123");
        when(request.getParam("limit")).thenReturn("1");
        assertThrows(ParameterValidationException.class, () -> finderValidator.validate(event));
    }

    @Test
    public void failOnWrongValues() {
        when(event.request()).thenReturn(request);
        when(request.getParam("param")).thenReturn("density20");
        when(request.getParam("value")).thenReturn("-0.123");
        when(request.getParam("limit")).thenReturn("-1");
        assertThrows(ParameterValidationException.class, () -> finderValidator.validate(event));
    }
}
