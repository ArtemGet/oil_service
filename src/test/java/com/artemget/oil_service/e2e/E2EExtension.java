package com.artemget.oil_service.e2e;

import com.artemget.oil_service.Host;
import com.artemget.oil_service.datasource.OilDataSource;
import com.artemget.oil_service.datasource.UserDataSource;
import com.artemget.oil_service.di.ApplicationDI;
import com.artemget.oil_service.di.MockDataSourceModule;
import com.artemget.oil_service.di.MockedAuthModule;
import com.artemget.oil_service.di.modules.*;
import com.google.inject.Injector;
import io.vertx.core.Vertx;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.web.client.WebClient;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class E2EExtension implements BeforeAllCallback, AfterAllCallback {
    public WebClient client;
    //mock implementation of OilDataSource
    public OilDataSource mockOilDataSource;
    //mock implementation of UserDataSource
    public UserDataSource mockUserDataSource;
    //mock implementation of JWTAuth
    public JWTAuth mockedJWTAuth;
    private Host host;

    @Override
    public void beforeAll(ExtensionContext context) {
        var mockContext = ApplicationDI
                .newBuilder()
                .addModules(
                        new ConfigModule(),
                        //mocked
                        new MockDataSourceModule(),
                        new RepositoryModule(),
                        new HttpModule(),
                        new ControllerModule(),
                        //mocked
                        new MockedAuthModule(),
                        new ValidatorModule(),
                        new ServiceModule(),
                        new ExecutorModule())
                .build();

        Injector injector = mockContext.getInjector();

        this.host = injector.getInstance(Host.class);
        this.host.startHostOnDefaultPort();

        this.mockUserDataSource = injector.getInstance(UserDataSource.class);
        this.mockOilDataSource = injector.getInstance(OilDataSource.class);
        this.mockedJWTAuth = injector.getInstance(JWTAuth.class);

        setUpHttpClient(injector.getInstance(Vertx.class));
    }

    @Override
    public void afterAll(ExtensionContext context) {
        this.client.close();
        this.host.terminate();
    }

    public void setUpHttpClient(Vertx vertx) {
        client = WebClient.create(vertx);
    }
}
