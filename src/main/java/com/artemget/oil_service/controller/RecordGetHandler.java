package com.artemget.oil_service.controller;

import com.artemget.oil_service.executor.ExecutorProvider;
import com.artemget.oil_service.model.User;
import com.artemget.oil_service.service.RecordService;
import com.artemget.oil_service.utils.RecordParser;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Singleton
public class RecordGetHandler implements Handler<RoutingContext> {
    private final RecordService recordService;
    private final ExecutorProvider executorProvider;

    @Inject
    public RecordGetHandler(RecordService recordService,
                            ExecutorProvider executorProvider) {
        this.recordService = recordService;
        this.executorProvider = executorProvider;
    }

    @Override
    public void handle(RoutingContext event) {
        var user = User.builder()
                .name(event.user().get("name"))
                .isAdmin(event.user().get("admin"))
                .build();

        if (!user.isAdmin()) {
            log.error("Admin roots required!");
            event.fail(403);
            return;
        }

        CompletableFuture.supplyAsync(recordService::getAllRecords,
                executorProvider.getExecutorService())
                .thenAccept((recordList) -> {
                    if (recordList.isEmpty()) {
                        log.error("Error: empty record table!");
                        event.fail(404);
                        return;
                    }
                    event.response()
                            .setStatusCode(200)
                            .putHeader("content-type", "application/json; charset=utf-8")
                            .end(RecordParser.toJson(recordList)
                                    .encodePrettily());
                })
                .exceptionally(throwable -> {
                    log.error("Error occurred while getting records!", throwable);
                    event.fail(500);
                    return null;
                });
    }
}
