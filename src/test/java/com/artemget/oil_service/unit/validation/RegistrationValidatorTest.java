package com.artemget.oil_service.unit.validation;

import com.artemget.oil_service.exception.ParameterValidationException;
import com.artemget.oil_service.validation.HttpValidator;
import com.artemget.oil_service.validation.RegistrationValidator;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RegistrationValidatorTest {
    private final HttpValidator validator = new RegistrationValidator();
    @Test
    public void shouldPassValidationOnCorrectParams() {
        RoutingContext event = mock(RoutingContext.class);
        when(event.getBodyAsJson())
                .thenReturn(new JsonObject()
                        .put("name", "user")
                        .put("password", "123")
                        .put("email", "user@user.com"));

        assertDoesNotThrow(() -> {
            validator.validate(event);
        });
    }

    @Test
    public void shouldFailOnEmptyBody() {
        RoutingContext event = mock(RoutingContext.class);
        when(event.getBodyAsJson()).thenReturn(null);

        assertThrows(ParameterValidationException.class,
                () -> validator.validate(event));

        when(event.getBodyAsJson()).thenReturn(new JsonObject());
        assertThrows(ParameterValidationException.class,
                () -> validator.validate(event));
    }

    @Test
    public void shouldFailOnEmptyBodyParams() {
        RoutingContext event = mock(RoutingContext.class);
        when(event.getBodyAsJson())
                .thenReturn(new JsonObject()
                        .put("name", "")
                        .put("password", "")
                        .put("email", ""));

        assertThrows(ParameterValidationException.class,
                () -> validator.validate(event));
    }

    @Test
    public void shouldFailOnCorruptedEmail() {
        RoutingContext event = mock(RoutingContext.class);
        when(event.getBodyAsJson())
                .thenReturn(new JsonObject()
                        .put("name", "user")
                        .put("password", "123")
                        .put("email", "user$usercom"));

        assertThrows(ParameterValidationException.class,
                () -> validator.validate(event));
    }
}
