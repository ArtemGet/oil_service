package com.artemget.oil_service.controller;

import com.artemget.oil_service.exception.ParameterValidationException;
import com.artemget.oil_service.executor.ExecutorProvider;
import com.artemget.oil_service.repository.reqest.OilRequest;
import com.artemget.oil_service.service.OilFinderService;
import com.artemget.oil_service.utils.OilParser;
import com.artemget.oil_service.validation.HttpValidator;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Singleton
public class FinderHandler implements Handler<RoutingContext> {
    private final HttpValidator finderValidator;
    private final ExecutorProvider executorProvider;
    private final OilFinderService finderService;

    @Inject
    public FinderHandler(@Named("finder_validator") HttpValidator finderValidator,
                         ExecutorProvider executorProvider,
                         OilFinderService oilUploadService) {
        this.finderValidator = finderValidator;
        this.executorProvider = executorProvider;
        this.finderService = oilUploadService;
    }

    @Override
    public void handle(RoutingContext event) {
        try {
            finderValidator.validate(event);
        } catch (ParameterValidationException e) {
            event.fail(400);
            log.error("Error: bad request", e);
            return;
        }
        String param = event.pathParam("param");
        double value = event.getBodyAsJson().getDouble("value");
        long limit = Long.parseLong(event.pathParam("limit"));

        CompletableFuture.supplyAsync(() -> finderService.findOils(new OilRequest(param, value, limit)),
                executorProvider.getExecutorService())
                .thenAccept(oilList -> {
                    if (oilList.isEmpty()) {
                        log.error("Error: no such oil");
                        event.fail(404);
                        return;
                    }
                    event.response()
                            .putHeader("Content-Type", "application/json")
                            .setStatusCode(200)
                            .end(OilParser.toJson(oilList)
                                    .encodePrettily());
                })
                .exceptionally(throwable -> {
                    log.error("Error: internal", throwable);
                    event.fail(500);
                    return null;
                });
    }
}
