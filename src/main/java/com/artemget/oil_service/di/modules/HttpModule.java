package com.artemget.oil_service.di.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
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
            Router router
    ) {
        return vertx.createHttpServer().requestHandler(router);
    }
}
