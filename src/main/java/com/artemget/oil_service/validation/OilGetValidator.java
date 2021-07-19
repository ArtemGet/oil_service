package com.artemget.oil_service.validation;

import com.google.common.base.Preconditions;
import io.vertx.ext.web.RoutingContext;

public class OilGetValidator implements HttpValidator {
    @Override
    public void setEvent(RoutingContext event) {
        var recordId = event.pathParam("record");
        Preconditions.checkNotNull(recordId);
        var recordIdParsed = Long.parseLong(recordId);
        Preconditions.checkArgument(recordIdParsed >= 0);
    }
}
