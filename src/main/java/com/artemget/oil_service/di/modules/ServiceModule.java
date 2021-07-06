package com.artemget.oil_service.di.modules;

import com.artemget.oil_service.model.Oil;
import com.artemget.oil_service.repository.OilRepository;
import com.artemget.oil_service.repository.RecordRepository;
import com.artemget.oil_service.repository.UserRepository;
import com.artemget.oil_service.service.FinderService;
import com.artemget.oil_service.service.OilFinderService;
import com.artemget.oil_service.service.OilUploadService;
import com.artemget.oil_service.service.UserService;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;

public class ServiceModule extends AbstractModule {
    @Override
    public void configure() {
        bind(new TypeLiteral<FinderService<Oil,Oil>>(){})
                .to(OilFinderService.class)
                .asEagerSingleton();
    }

    @Provides
    @Singleton
    public UserService provideUserService(UserRepository userRepository) {
        return new UserService(userRepository);
    }

    @Provides
    @Singleton
    public OilUploadService provideOilStoreService(RecordRepository recordRepository, OilRepository oilRepository) {
        return new OilUploadService(oilRepository, recordRepository);
    }
}
