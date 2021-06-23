package com.artemget.oil_service;

import com.artemget.oil_service.di.ApplicationDI;
import com.artemget.oil_service.di.modules.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Application {
    public static void main(String[] args) {
        var applicationContext = ApplicationDI
                .newBuilder()
                .addModules(
                        new ConfigModule(),
                        new DatasourceModule(),
                        new RepositoryModule(),
                        new HttpModule(),
                        new ControllerModule(),
                        new ValidatorModule(),
                        new AuthModule())
                .build();

        Host host = applicationContext.getInjector().getInstance(Host.class);
        try {
            host.startHostOnDefaultPort();
        } catch (Exception e) {
            log.error("Error configuring http server", e);
            host.terminate();
            System.exit(-1);
        }
    }
}
