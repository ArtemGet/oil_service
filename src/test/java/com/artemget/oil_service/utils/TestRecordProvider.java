package com.artemget.oil_service.utils;

import com.artemget.oil_service.model.Record;
import io.vertx.core.json.JsonObject;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

@UtilityClass
public class TestRecordProvider {
    private static final LocalDateTime time = LocalDateTime.of(2010, 10, 10, 10, 10, 10, 10);

    public static Record provideValidRecordOne() {
        return Record.builder()
                .id(1)
                .userName("1")
                .inserted(1)
                .corrupted(1)
                .insertionDate(time)
                .build();
    }

    public static JsonObject provideValidRecordOneJson() {
        return new JsonObject().put("id", 1)
                .put("user_name", "1")
                .put("inserted", 1)
                .put("corrupted", 1)
                .put("insertion_date", "2010-10-10 10:10:10");
    }

    public static Record provideValidRecordTwo() {
        return Record.builder()
                .id(2)
                .userName("2")
                .inserted(2)
                .corrupted(2)
                .insertionDate(time)
                .build();
    }

    public static JsonObject provideValidRecordTwoJson() {
        return new JsonObject().put("id", 2)
                .put("user_name", "2")
                .put("inserted", 2)
                .put("corrupted", 2)
                .put("insertion_date", "2010-10-10 10:10:10");
    }
}
