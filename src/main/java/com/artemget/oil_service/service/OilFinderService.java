package com.artemget.oil_service.service;

import com.artemget.oil_service.model.Oil;
import com.artemget.oil_service.model.Record;
import com.artemget.oil_service.repository.reqest.OilRequest;
import com.artemget.oil_service.repository.OilRepository;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.List;

@Singleton
public class OilFinderService {
    private final OilRepository repository;

    @Inject
    public OilFinderService(OilRepository repository) {
        this.repository = repository;
    }

    public List<Oil> findOils(OilRequest oilRequest) {
        return repository.getOils(oilRequest);
    }

    public List<Oil> getOilsByRecord(Record record) {
        return null;
    }
}
