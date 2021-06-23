package com.artemget.oil_service.di.modules;

import com.artemget.oil_service.controller.LoginHandler;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class ControllerModule extends AbstractModule {
    @Provides
    @Singleton
    public Router setUpHandlers(Vertx vertx,
                                LoginHandler handler) {
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        router.route(HttpMethod.GET, "/login").handler(handler);
        return router;
    }
}
