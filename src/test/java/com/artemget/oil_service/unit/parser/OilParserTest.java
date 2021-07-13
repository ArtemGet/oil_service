package com.artemget.oil_service.unit.parser;

import com.artemget.oil_service.utils.OilParser;
import com.artemget.oil_service.utils.TestOilProvider;
import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OilParserTest {

    @Test
    public void onSuccess() {
        var expected = new JsonObject()
                .put("oils", List.of(
                        TestOilProvider.provideValidOneJson(),
                        TestOilProvider.provideValidTwoJson()));
        var actual = OilParser
                .toJson(List.of(
                        TestOilProvider.provideValidOne(),
                        TestOilProvider.provideValidTwo()));
        assertEquals(expected, actual);
    }
}
