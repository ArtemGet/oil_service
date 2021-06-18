package com.artemget.oil_service.di.modules;

import com.artemget.oil_service.controller.MainPageHandler;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;

public class ControllerModule extends AbstractModule {

    @Provides
    @Singleton
    public Router setUpHandlers(Vertx vertx, MainPageHandler handler) {
        Router router = Router.router(vertx);
        router.route(HttpMethod.GET, "/").handler(handler);
        return router;
    }
}
