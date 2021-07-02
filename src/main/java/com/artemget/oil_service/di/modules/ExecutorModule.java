package com.artemget.oil_service.di.modules;

import com.artemget.oil_service.executor.ExecutorProvider;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import java.util.concurrent.Executors;

public class ExecutorModule extends AbstractModule {
    @Provides
    @Singleton
    public ExecutorProvider createExecutorProvider() {
        return new ExecutorProvider(Executors
                .newCachedThreadPool(new ThreadFactoryBuilder()
                        .setNameFormat("worker-pool-%d")
                        .build()));
    }
}
