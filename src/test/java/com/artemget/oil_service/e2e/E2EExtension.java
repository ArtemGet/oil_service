package com.artemget.oil_service.e2e;

import com.artemget.oil_service.Host;
import com.artemget.oil_service.di.ApplicationDI;
import com.artemget.oil_service.di.MockDataSourceModule;
import com.artemget.oil_service.di.modules.*;
import com.artemget.oil_service.repository.OilRepository;
import com.google.inject.Injector;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class E2EExtension implements BeforeAllCallback, AfterAllCallback {
    public WebClient client;
    //mock implementation of OilRepository
    public OilRepository repository;
    private Host host;

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        var mockContext = ApplicationDI
                .newBuilder()
                .addModules(
                        new ConfigModule(),
                        //mocked
                        new MockDataSourceModule(),
                        new RepositoryModule(),
                        new HttpModule(),
                        new ControllerModule())
                .build();

        Injector injector = mockContext.getInjector();

        this.host = injector.getInstance(Host.class);
        this.host.startHostOnDefaultPort();
        this.repository = injector.getInstance(OilRepository.class);
        setUpHttpClient(injector.getInstance(Vertx.class));
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        this.client.close();
        this.host.terminate();
    }

    public void setUpHttpClient(Vertx vertx) {
        client = WebClient.create(vertx);
    }
}
