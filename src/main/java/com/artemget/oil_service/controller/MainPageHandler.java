package com.artemget.oil_service.controller;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MainPageHandler implements Handler<RoutingContext> {

    @Override
    public void handle(RoutingContext event) {
        event.response()
                .putHeader("content-type", "text/html; charset=utf-8")
                .sendFile("src/main/resources/view_pages/main_page.html");
    }
}
