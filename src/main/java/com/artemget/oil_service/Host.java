package com.artemget.oil_service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.vertx.core.http.HttpServer;

@Singleton
public class Host {
    private final HttpServer httpServer;

    @Inject
    public Host(HttpServer httpServer) {
        this.httpServer = httpServer;
    }

    public void startHostOnDefaultPort() {
        httpServer.listen(8080);
    }

    public void terminate() {
        httpServer.close();
    }
}
