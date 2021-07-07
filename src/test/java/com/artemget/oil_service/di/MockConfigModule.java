package com.artemget.oil_service.di;

import com.artemget.oil_service.config.ApplicationConfig;
import com.artemget.oil_service.config.SQLConfig;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import static org.mockito.Mockito.mock;

public class MockConfigModule extends AbstractModule {
    //TODO set properties properly!
    @Provides
    @Singleton
    public ApplicationConfig config(@Named("mock_sql_config") SQLConfig sqlConfig) {
        //TEMPORARY!!!
        System.setProperty("keyStorePath","C:/Users/KekuS/.jdks/adopt-openjdk-14.0.2/bin/keystore.jceks");
        System.setProperty("secretBuffer","test");
        System.setProperty("keyStorePassword","12345678");

        return new ApplicationConfig(sqlConfig, sqlConfig);
    }

    @Provides
    @Named("mock_sql_config")
    @Singleton
    public SQLConfig provideMockSQlConfig() {
        return mock(SQLConfig.class);
    }
}
