package com.artemget.oil_service.controller;

import com.artemget.oil_service.exception.ParameterValidationException;
import com.artemget.oil_service.executor.ExecutorProvider;
import com.artemget.oil_service.model.User;
import com.artemget.oil_service.service.UserService;
import com.artemget.oil_service.validation.HttpValidator;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.JWTOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Singleton
public class LoginHandler implements Handler<RoutingContext> {
    private final JWTAuth jwtAuthProvider;
    private final HttpValidator loginValidator;
    private final UserService userService;
    private final ExecutorProvider mainProvider;

    @Inject
    public LoginHandler(JWTAuth jwtAuthProvider,
                        @Named("login_validator") HttpValidator loginValidator,
                        UserService userService,
                        ExecutorProvider mainProvider) {
        this.jwtAuthProvider = jwtAuthProvider;
        this.loginValidator = loginValidator;
        this.userService = userService;
        this.mainProvider = mainProvider;
    }

    @Override
    public void handle(RoutingContext event) {
        try {
            loginValidator.validate(event);
        } catch (ParameterValidationException e) {
            event.fail(400);
            log.error("Error: login bad request", e);
            return;
        }
        var body = event.getBodyAsJson();
        var user = User.builder()
                .name(event.pathParam("name"))
                .password(body.getString("password"))
                .build();

        CompletableFuture
                .supplyAsync(() -> userService.isAdmin(user), mainProvider.getExecutorService())
                .thenAccept((isAdmin) ->
                        event.response()
                                .setStatusCode(200)
                                .putHeader("content-type", "application/json; charset=utf-8")
                                .end(new JsonObject()
                                        .put("token", jwtAuthProvider
                                                .generateToken(new JsonObject()
                                                        .put("name", user.getName())
                                                        .put("admin", isAdmin)))
                                        .encodePrettily()))
                .exceptionally(throwable -> {
                    event.fail(404);
                    log.error("failed to check user role/no such user", throwable);
                    return null;
                });
    }
}
