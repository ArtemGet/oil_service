package com.artemget.oil_service.di.modules;

import com.artemget.oil_service.repository.OilRepository;
import com.artemget.oil_service.repository.OilRepositoryImpl;
import com.google.inject.AbstractModule;

public class RepositoryModule extends AbstractModule {
    @Override
    public void configure() {
        bind(OilRepository.class)
                .to(OilRepositoryImpl.class)
                .asEagerSingleton();
    }
}
