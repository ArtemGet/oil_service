package com.artemget.oil_service.di.modules;

import com.artemget.oil_service.controller.*;
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
                                RegistrationHandler registrationHandler,
                                UploadHandler uploadHandler,
                                FinderHandler finderHandler,
                                RecordGetHandler recordGetHandler) {
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create().setDeleteUploadedFilesOnEnd(true));
        router.route("/api/*").handler(JWTAuthHandler.create(jwtAuthProvider));

        router.route(HttpMethod.GET, "/users/:name").handler(loginHandler);
        router.route(HttpMethod.POST, "/users").handler(registrationHandler);

        router.route(HttpMethod.POST, "/api/oils").handler(uploadHandler);
        router.route(HttpMethod.GET, "/api/oils/:param/:limit").handler(finderHandler);
//        router.route(HttpMethod.GET, "/api/oils/:record-id").handler(oilGetHandler);
//        router.route(HttpMethod.DELETE, "/api/records/").handler(recordDeleteHandler);
        router.route(HttpMethod.GET, "/api/records").handler(recordGetHandler);
        return router;
    }
}
