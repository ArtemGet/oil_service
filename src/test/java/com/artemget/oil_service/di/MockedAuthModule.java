package com.artemget.oil_service.di;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.vertx.ext.auth.jwt.JWTAuth;

import static org.mockito.Mockito.mock;

public class MockedAuthModule extends AbstractModule {

    @Provides
    @Singleton
    public JWTAuth provideJWTAuthProvider() {
        return mock(JWTAuth.class);
    }
}
