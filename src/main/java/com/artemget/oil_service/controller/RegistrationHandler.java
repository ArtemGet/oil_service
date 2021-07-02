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
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Singleton
public class RegistrationHandler implements Handler<RoutingContext> {
    private final JWTAuth jwtAuthProvider;
    private final HttpValidator registrationValidator;
    private final UserService userService;
    private final ExecutorProvider mainProvider;

    @Inject
    public RegistrationHandler(JWTAuth jwtAuthProvider,
                               @Named("registration_validator") HttpValidator registrationValidator,
                               UserService userService,
                               ExecutorProvider mainProvider) {
        this.jwtAuthProvider = jwtAuthProvider;
        this.registrationValidator = registrationValidator;
        this.userService = userService;
        this.mainProvider = mainProvider;
    }

    @Override
    public void handle(RoutingContext event) {
        try {
            registrationValidator.validate(event);
        } catch (ParameterValidationException e) {
            event.fail(400);
            log.error("Error: registration bad request", e);
            return;
        }
        var body = event.getBodyAsJson();
        var user = User.builder()
                .name(body.getString("name"))
                .password(body.getString("password"))
                .email(body.getString("email"))
                .build();

        CompletableFuture.runAsync(() -> {
                    userService.registerUser(user);
                },
                mainProvider.getExecutorService())
                .thenRun(() ->
                        event.response()
                                .setStatusCode(200)
                                .putHeader("content-type", "application/json; charset=utf-8")
                                .end(new JsonObject()
                                        .put("token", jwtAuthProvider
                                                .generateToken(new JsonObject()
                                                        .put("name", user.getName())
                                                        .put("admin", false)))
                                        .encodePrettily()))
                .exceptionally((t) -> {
                    event.fail(409);
                    log.error("User registration error", t);
                    return null;
                });
    }
}
