package com.artemget.oil_service.validation;

import com.google.common.base.Preconditions;
import io.vertx.ext.web.RoutingContext;

public class LoginValidator implements HttpValidator {
    @Override
    public void setEvent(RoutingContext event) {
        var body = event.getBodyAsJson();
        Preconditions.checkArgument(!body.getString("name").isEmpty());
        Preconditions.checkArgument(!body.getString("password").isEmpty());
        Preconditions.checkArgument(event.getBodyAsJson().fieldNames().size() == 2);
    }
}
