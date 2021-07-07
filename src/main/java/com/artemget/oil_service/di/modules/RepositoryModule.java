package com.artemget.oil_service.di.modules;

import com.artemget.oil_service.repository.*;
import com.google.inject.AbstractModule;

public class RepositoryModule extends AbstractModule {
    @Override
    public void configure() {
        bind(OilRepository.class)
                .to(OilRepositoryImpl.class)
                .asEagerSingleton();

        bind(UserRepository.class)
                .to(UserRepositoryImpl.class)
                .asEagerSingleton();
        bind(RecordRepository.class)
                .to(RecordRepositoryImpl.class)
                .asEagerSingleton();
    }
}
