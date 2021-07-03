package com.artemget.oil_service.service;

import com.artemget.oil_service.model.Oil;
import com.artemget.oil_service.model.User;
import com.artemget.oil_service.repository.OilRepository;
import com.artemget.oil_service.repository.RecordRepository;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.List;

@Singleton
public class OilStoreService {
    private final OilRepository oilRepository;
    private final RecordRepository recordRepository;

    @Inject
    public OilStoreService(OilRepository oilRepository, RecordRepository recordRepository) {
        this.oilRepository = oilRepository;
        this.recordRepository = recordRepository;
    }

    public void storeOil(List<Oil> oilList, User user) {
       var recordId = recordRepository.addRecord(user);
        try {
            oilRepository.addOilList(oilList, recordId);
        } catch (Exception e) {
            recordRepository.removeRecord(recordId);
            throw new RuntimeException(e);
        }
    }
}
