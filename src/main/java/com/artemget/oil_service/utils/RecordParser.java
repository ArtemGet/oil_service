package com.artemget.oil_service.utils;

import com.artemget.oil_service.model.Record;
import io.vertx.core.json.JsonObject;
import lombok.experimental.UtilityClass;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class RecordParser {
    public static JsonObject toJson(List<Record> recordList) {
        return new JsonObject().put("records",
                recordList.stream()
                        .map(record -> new JsonObject()
                                .put("id", record.getId())
                                .put("user_name", record.getUserName())
                                .put("inserted", record.getInserted())
                                .put("corrupted", record.getCorrupted())
                                .put("insertion_date", record.getInsertionDate()
                                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))))
                        .collect(Collectors.toList()));
    }
}
