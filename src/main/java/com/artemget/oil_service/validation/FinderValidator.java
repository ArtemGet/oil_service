package com.artemget.oil_service.validation;

import com.artemget.oil_service.utils.OilParams;
import com.google.common.base.Preconditions;
import io.vertx.ext.web.RoutingContext;

public class FinderValidator implements HttpValidator {
    @Override
    public void setEvent(RoutingContext event) {
        //enough to prevent SQL injection
        var param = OilParams.isDbValue(event.request().getParam("param"));
        Preconditions.checkNotNull(param);

        double value = Double.parseDouble(event.request().getParam("value"));
        Preconditions.checkArgument(value > 0);

        long limit = Long.parseLong(event.request().getParam("limit"));
        Preconditions.checkArgument(limit > 0);
    }
}
