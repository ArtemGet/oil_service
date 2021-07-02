package com.artemget.oil_service.di.modules;

import com.artemget.oil_service.config.ApplicationConfig;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.net.JksOptions;
import io.vertx.ext.web.Router;

public class HttpModule extends AbstractModule {
    @Provides
    @Singleton
    public Vertx provideVerticle() {
        return Vertx.vertx();
    }

    @Provides
    @Singleton
    public HttpServer provideHttpServer(
            Vertx vertx,
            Router router,
            ApplicationConfig applicationConfig
    ) {
        HttpServerOptions secureOptions = new HttpServerOptions();
        secureOptions.setSsl(true)
                .setKeyStoreOptions(new JksOptions()
                        .setPath(applicationConfig.getTokenConfig().getKeyStorePath())
                        .setPassword(applicationConfig.getTokenConfig().getKeyStorePassword()));

        return vertx.createHttpServer(secureOptions)
                .requestHandler(router);
    }
}
