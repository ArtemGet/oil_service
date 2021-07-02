package com.artemget.oil_service.unit;

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
    @Test
    public void shouldPassValidationOnCorrectParams() {
        RoutingContext event = mock(RoutingContext.class);
        when(event.getBodyAsJson())
                .thenReturn(new JsonObject()
                        .put("name", "user")
                        .put("password", "123")
                        .put("email", "user@user.com"));

        HttpValidator validator = new RegistrationValidator();
        assertDoesNotThrow(() -> {
            validator.validate(event);
        });
    }

    @Test
    public void shouldFailOnEmptyBody() {
        RoutingContext event = mock(RoutingContext.class);
        when(event.getBodyAsJson()).thenReturn(null);

        HttpValidator validator = new RegistrationValidator();
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

        HttpValidator validator = new RegistrationValidator();
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

        HttpValidator validator = new RegistrationValidator();
        assertThrows(ParameterValidationException.class,
                () -> validator.validate(event));
    }
}
