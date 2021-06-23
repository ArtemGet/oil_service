package com.artemget.oil_service.executor;

import com.google.inject.Inject;
import lombok.Getter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

@Getter
public class ExecutorProvider {
    private final ExecutorService executorService;

    @Inject
    public ExecutorProvider(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public void shutDownAllExecutors() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(2000, TimeUnit.MILLISECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }
}
