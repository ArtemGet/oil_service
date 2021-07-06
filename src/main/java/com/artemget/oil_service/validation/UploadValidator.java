package com.artemget.oil_service.validation;

import com.google.common.base.Preconditions;
import io.vertx.ext.web.RoutingContext;

public class UploadValidator implements HttpValidator {
    @Override
    public void setEvent(RoutingContext event) {
        Preconditions.checkArgument(!event.fileUploads().isEmpty());
        Preconditions.checkArgument(event.fileUploads().size() == 1);

        var file = event.fileUploads().stream().findFirst().get();
        Preconditions.checkArgument(file.fileName().endsWith(".xlsx"));
    }
}
