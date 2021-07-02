package com.artemget.oil_service.di.modules;

import com.artemget.oil_service.config.ApplicationConfig;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.vertx.core.Vertx;
import io.vertx.ext.auth.JWTOptions;
import io.vertx.ext.auth.KeyStoreOptions;
import io.vertx.ext.auth.PubSecKeyOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;

public class AuthModule extends AbstractModule {
    @Provides
    @Singleton
    public JWTAuth provideJWTAuthProvider(Vertx vertx, ApplicationConfig applicationConfig) {
        JWTAuthOptions authConfig = new JWTAuthOptions()
                .setKeyStore(new KeyStoreOptions()
                        .setPath(applicationConfig.getTokenConfig().getKeyStorePath())
                        .setPassword(applicationConfig.getTokenConfig().getKeyStorePassword()))
                .addPubSecKey(new PubSecKeyOptions()
                        .setAlgorithm("HS256")
                        .setBuffer(applicationConfig.getTokenConfig().getSecretBuffer()))
                .setJWTOptions(new JWTOptions()
                        .setExpiresInMinutes(1440));
        return JWTAuth.create(vertx, authConfig);
    }
}
