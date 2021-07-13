package com.artemget.oil_service.di.modules;

import com.artemget.oil_service.controller.FinderController;
import com.artemget.oil_service.controller.UploadHandler;
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
                                RegistrationHandler registrationHandler,
                                UploadHandler uploadHandler,
                                FinderController finderController) {
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create().setDeleteUploadedFilesOnEnd(true));
        router.route("/api/*").handler(JWTAuthHandler.create(jwtAuthProvider));

        router.route(HttpMethod.GET, "/users/user").handler(loginHandler);
        router.route(HttpMethod.POST, "/users/user").handler(registrationHandler);

        router.route(HttpMethod.POST, "/api/handbooks/handbook").handler(uploadHandler);
        router.route(HttpMethod.GET, "/api/handbooks/handbook/oils/oil").handler(finderController);
        return router;
    }
}
