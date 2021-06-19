package com.artemget.oil_service.di;

import com.artemget.oil_service.datasource.OilDataSource;
import com.artemget.oil_service.datasource.SQLOilSource;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.jdbi.v3.core.Jdbi;

import static org.mockito.Mockito.mock;

public class MockDataSourceModule extends AbstractModule {
    @Override
    public void configure() {
        bind(OilDataSource.class)
                .to(SQLOilSource.class)
                .asEagerSingleton();
    }

    @Provides
    @Singleton
    public Jdbi provideJdbiInstance() {
        return mock(Jdbi.class);
    }
}
