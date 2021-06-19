package com.artemget.oil_service.service;

import com.artemget.oil_service.model.Oil;
import com.artemget.oil_service.repository.OilRepository;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class OilFinderService implements FinderService<Oil, Oil> {
    private final OilRepository repository;

    @Inject
    public OilFinderService(OilRepository repository) {
        this.repository = repository;
    }

    @Override
    public Oil find(Oil oil) {
        return null;
    }
}
