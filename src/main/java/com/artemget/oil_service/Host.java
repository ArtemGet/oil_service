package com.artemget.oil_service;

import com.artemget.oil_service.executor.ExecutorProvider;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.vertx.core.http.HttpServer;

@Singleton
public class Host {
    private final HttpServer httpServer;
    private final ExecutorProvider mainProvider;

    @Inject
    public Host(HttpServer httpServer, ExecutorProvider mainProvider) {
        this.httpServer = httpServer;
        this.mainProvider = mainProvider;
    }

    public void startHostOnDefaultPort() {
        httpServer.listen(8080);
    }

    public void terminate() {
        mainProvider.shutDownAllExecutors();
        httpServer.close();
    }
}
