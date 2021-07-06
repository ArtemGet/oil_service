package com.artemget.oil_service.di;

import com.artemget.oil_service.datasource.*;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import static org.mockito.Mockito.mock;

public class MockDataSourceModule extends AbstractModule {
    @Provides
    @Singleton
    public OilDataSource provideMockOilDataSource() {
        return mock(SQLOilSource.class);
    }

    @Provides
    @Singleton
    public UserDataSource provideMockUserDataSource() {
        return mock(SQLUserSource.class);
    }

    @Provides
    @Singleton
    public RecordDataSource provideMockRecordDataSource() {
        return mock(SQLRecordDataSource.class);
    }
}
