package com.artemget.oil_service.di.modules;

import com.artemget.oil_service.controller.LoginHandler;
import com.artemget.oil_service.controller.RegistrationHandler;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.JWTAuthHandler;

public class ControllerModule extends AbstractModule {
    @Provides
    @Singleton
    public Router setUpHandlers(Vertx vertx,
                                JWTAuth jwtAuthProvider,
                                LoginHandler loginHandler,
                                RegistrationHandler registrationHandler) {
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        router.route("/api/resources/*").handler(JWTAuthHandler.create(jwtAuthProvider));

        router.route(HttpMethod.GET, "/login").handler(loginHandler);
        router.route(HttpMethod.POST, "/register").handler(registrationHandler);
        return router;
    }
}
