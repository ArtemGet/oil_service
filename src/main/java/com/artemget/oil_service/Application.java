package com.artemget.oil_service;

import com.artemget.oil_service.di.ApplicationDI;
import com.artemget.oil_service.di.modules.ConfigModule;
import com.artemget.oil_service.di.modules.DatasourceModule;
import com.artemget.oil_service.di.modules.RepositoryModule;

public class Application {
    public static void main(String[] args) {
        var applicationContext = ApplicationDI
                .newBuilder()
                .addModules(
                        new ConfigModule(),
                        new DatasourceModule(),
                        new RepositoryModule())
                .build();
        var injector = applicationContext.getInjector();
    }
}
