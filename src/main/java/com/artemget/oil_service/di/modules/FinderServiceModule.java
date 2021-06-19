package com.artemget.oil_service.di.modules;

import com.artemget.oil_service.model.Oil;
import com.artemget.oil_service.service.FinderService;
import com.artemget.oil_service.service.OilFinderService;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;

public class FinderServiceModule extends AbstractModule {
    @Override
    public void configure() {
        bind(new TypeLiteral<FinderService<Oil,Oil>>(){})
                .to(OilFinderService.class)
                .asEagerSingleton();
    }
}
