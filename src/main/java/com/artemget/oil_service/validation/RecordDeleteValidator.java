package com.artemget.oil_service.validation;

import com.google.common.base.Preconditions;
import io.vertx.ext.web.RoutingContext;

import java.util.stream.Collectors;

public class RecordDeleteValidator implements HttpValidator {
    @Override
    public void setEvent(RoutingContext event) {
        var jsonArray = event.getBodyAsJson().getJsonArray("record_id_array");
        Preconditions.checkNotNull(jsonArray);
        Preconditions.checkArgument(!jsonArray.isEmpty());
        var idArray = jsonArray.stream()
                .map(val -> Long.parseLong(val.toString()))
                .collect(Collectors.toList());
        idArray.forEach((id) ->
                Preconditions.checkArgument(id >= 0));
    }
}
