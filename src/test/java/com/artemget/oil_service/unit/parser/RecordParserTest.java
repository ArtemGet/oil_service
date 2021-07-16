package com.artemget.oil_service.unit.parser;

import com.artemget.oil_service.utils.RecordParser;
import com.artemget.oil_service.utils.TestRecordProvider;
import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecordParserTest {

    @Test
    public void onSuccess() {
        LocalDateTime time = LocalDateTime.of(2010, 10, 10, 10, 10, 10, 10);
        var expected = new JsonObject().put("records",
                List.of(TestRecordProvider.provideValidRecordOneJson(),
                        TestRecordProvider.provideValidRecordTwoJson()));

        var actual = RecordParser.toJson(
                List.of(TestRecordProvider.provideValidRecordOne(),
                        TestRecordProvider.provideValidRecordTwo()));
        assertEquals(expected, actual);
    }
}
