package com.artemget.oil_service.unit;

import com.artemget.oil_service.executor.ExecutorProvider;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExecutorProviderTest {
    @Test
    public void shouldStop() {
        ExecutorProvider executorProvider = new ExecutorProvider(Executors
                .newCachedThreadPool(new ThreadFactoryBuilder()
                        .setNameFormat("worker-pool-%d")
                        .build()));

        executorProvider
                .getExecutorService()
                .submit(() -> {
                    //blocking code
                    while (true) {

                    }
                });
        executorProvider.shutDownAllExecutors();
        assertTrue(executorProvider.getExecutorService().isShutdown());
    }
}
