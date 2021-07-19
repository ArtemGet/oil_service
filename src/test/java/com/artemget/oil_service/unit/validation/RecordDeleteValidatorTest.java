package com.artemget.oil_service.unit.validation;

import com.artemget.oil_service.exception.ParameterValidationException;
import com.artemget.oil_service.validation.HttpValidator;
import com.artemget.oil_service.validation.RecordDeleteValidator;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class RecordDeleteValidatorTest {
    private final HttpValidator recordDeleteValidator = new RecordDeleteValidator();
    private final RoutingContext event = mock(RoutingContext.class);

    @Test
    public void onSuccess() {
        when(event.getBodyAsJson()).thenReturn(new JsonObject().put("record_id_array", List.of(1L, 2L, 3L)));
        assertDoesNotThrow(() -> recordDeleteValidator.validate(event));
        reset(event);
    }

    @Test
    public void failOnNullArray() {
        when(event.getBodyAsJson()).thenReturn(new JsonObject());
        assertThrows(ParameterValidationException.class, () -> recordDeleteValidator.validate(event));
        reset(event);
    }

    @Test
    public void failOnEmptyArray() {
        when(event.getBodyAsJson()).thenReturn(new JsonObject().put("record_id_array", List.of()));
        assertThrows(ParameterValidationException.class, () -> recordDeleteValidator.validate(event));
        reset(event);
    }

    @Test
    public void failOnInvalidFormat() {
        when(event.getBodyAsJson()).thenReturn(new JsonObject().put("record_id_array", List.of("one", "two", "three")));
        assertThrows(ParameterValidationException.class, () -> recordDeleteValidator.validate(event));
        reset(event);
    }

    @Test
    public void failOnIdBelowZero() {
        when(event.getBodyAsJson()).thenReturn(new JsonObject().put("record_id_array", List.of(-1L, -2L, -3L)));
        assertThrows(ParameterValidationException.class, () -> recordDeleteValidator.validate(event));
        reset(event);
    }
}
