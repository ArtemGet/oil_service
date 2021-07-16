package com.artemget.oil_service.controller;

import com.artemget.oil_service.exception.ParameterValidationException;
import com.artemget.oil_service.executor.ExecutorProvider;
import com.artemget.oil_service.model.Oil;
import com.artemget.oil_service.model.OilData;
import com.artemget.oil_service.model.User;
import com.artemget.oil_service.service.OilUploadService;
import com.artemget.oil_service.utils.XlsxParser;
import com.artemget.oil_service.validation.HttpValidator;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import io.vertx.core.Handler;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Singleton
public class UploadHandler implements Handler<RoutingContext> {
    private final HttpValidator uploadValidator;
    private final ExecutorProvider executorProvider;
    private final OilUploadService oilUploadService;

    @Inject
    public UploadHandler(@Named("upload_validator") HttpValidator uploadValidator,
                         ExecutorProvider executorProvider,
                         OilUploadService oilUploadService) {
        this.uploadValidator = uploadValidator;
        this.executorProvider = executorProvider;
        this.oilUploadService = oilUploadService;
    }

    //TODO close file upload resources properly
    @Override
    public void handle(RoutingContext event) {
        try {
            uploadValidator.validate(event);
        } catch (ParameterValidationException e) {
            e.printStackTrace();
            log.error("Bad request", e);
            event.fail(400);
            return;
        }

        var user = User.builder()
                .name(event.user().get("name"))
                .isAdmin(event.user().get("admin"))
                .build();

        if (!user.isAdmin()) {
            log.error("Admin roots required!");
            event.fail(403);
            return;
        }

        CompletableFuture.supplyAsync(() -> getAndParseData(event), executorProvider.getExecutorService())
                .thenApply((oilList) -> store(oilList, user))
                .thenAccept((succeed) -> {
                    if (!succeed) {
                        log.error("Error: attempt to insert already existing data");
                        event.fail(409);
                        return;
                    }
                    event.response()
                            .setStatusCode(201)
                            .end();
                })
                .exceptionally(throwable -> {
                    event.fail(500);
                    log.error("File store error", throwable);
                    return null;
                });
    }

    private OilData getAndParseData(RoutingContext event) {
        FileUpload fileUpload = event.fileUploads().stream()
                .findFirst()
                .get();
        return XlsxParser.parseXLSXFileToModel(fileUpload.uploadedFileName());
    }

    private boolean store(OilData oilData, User user) {
        return oilUploadService.storeOil(oilData, user);
    }
}
