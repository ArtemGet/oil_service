package com.artemget.oil_service.controller;

import com.artemget.oil_service.exception.ParameterValidationException;
import com.artemget.oil_service.executor.ExecutorProvider;
import com.artemget.oil_service.model.User;
import com.artemget.oil_service.service.RecordService;
import com.artemget.oil_service.validation.HttpValidator;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@Singleton
public class RecordDeleteHandler implements Handler<RoutingContext> {
    private final RecordService recordService;
    private final ExecutorProvider executorProvider;
    private final HttpValidator recordDeleteValidator;

    @Inject
    public RecordDeleteHandler(RecordService recordService,
                               ExecutorProvider executorProvider,
                               @Named("record_delete_validator") HttpValidator recordDeleteValidator) {
        this.recordService = recordService;
        this.executorProvider = executorProvider;
        this.recordDeleteValidator = recordDeleteValidator;
    }

    @Override
    public void handle(RoutingContext event) {
        try {
            recordDeleteValidator.validate(event);
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

        var idArray = event.getBodyAsJson()
                .getJsonArray("record_id_array").stream()
                .map(val -> Long.parseLong(val.toString()))
                .collect(Collectors.toList());

        CompletableFuture.runAsync(() -> recordService.clearRecords(idArray),
                executorProvider.getExecutorService())
                .thenRun(() ->
                        event.response()
                                .setStatusCode(201)
                                .end())
                .exceptionally(throwable -> {
                    log.error("Error: failed to remove records");
                    event.fail(500);
                    return null;
                });
    }
}
