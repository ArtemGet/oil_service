package com.artemget.oil_service.validation;

import com.google.common.base.Preconditions;
import io.vertx.ext.web.RoutingContext;

public class UploadValidator implements HttpValidator {
    @Override
    public void setEvent(RoutingContext event) {
        var uploads = event.fileUploads();
        Preconditions.checkArgument(!uploads.isEmpty());
        Preconditions.checkArgument(uploads.size() == 1);

        var file = uploads.stream().findFirst().get();
        Preconditions.checkArgument(file.fileName().endsWith(".xlsx"));
    }
}
