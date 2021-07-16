package com.artemget.oil_service.controller;

import com.artemget.oil_service.exception.ParameterValidationException;
import com.artemget.oil_service.executor.ExecutorProvider;
import com.artemget.oil_service.model.User;
import com.artemget.oil_service.service.OilFinderService;
import com.artemget.oil_service.service.RecordService;
import com.artemget.oil_service.utils.OilParser;
import com.artemget.oil_service.validation.HttpValidator;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Singleton
public class OilGetHandler implements Handler<RoutingContext> {
    private final OilFinderService oilFinderService;
    private final RecordService recordService;
    private final ExecutorProvider executorProvider;
    private final HttpValidator oilGetValidator;

    @Inject
    public OilGetHandler(OilFinderService oilFinderService,
                         RecordService recordService, ExecutorProvider executorProvider,
                         @Named("oil_get_validator") HttpValidator oilGetValidator) {
        this.oilFinderService = oilFinderService;
        this.recordService = recordService;
        this.executorProvider = executorProvider;
        this.oilGetValidator = oilGetValidator;
    }

    @Override
    public void handle(RoutingContext event) {
        try {
            oilGetValidator.validate(event);
        } catch (ParameterValidationException e) {
            event.fail(400);
            log.error("Error: registration bad request", e);
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

        long recordId = Long.parseLong(event.pathParam("record"));

        CompletableFuture.supplyAsync(() -> oilFinderService.getOilsByRecord(recordId),
                executorProvider.getExecutorService())
                .thenAccept((oilList) -> {
                    if (oilList.isEmpty()) {
                        recordService.clearRecords(List.of(recordId));
                        log.warn("Warn: record with empty oils were deleted");
                        event.fail(404);
                        return;
                    }
                    event.response()
                            .setStatusCode(200)
                            .putHeader("content-type", "application/json; charset=utf-8")
                            .end(OilParser.toJson(oilList)
                                    .encodePrettily());
                })
                .exceptionally(throwable -> {
                    log.error("Error occurred while getting records!", throwable);
                    event.fail(500);
                    return null;
                });
    }
}
