package com.artemget.oil_service.service;

import com.artemget.oil_service.model.Oil;
import com.artemget.oil_service.model.User;
import com.artemget.oil_service.repository.OilRepository;
import com.artemget.oil_service.repository.RecordRepository;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.List;

@Singleton
public class OilUploadService {
    private final OilRepository oilRepository;
    private final RecordRepository recordRepository;

    @Inject
    public OilUploadService(OilRepository oilRepository, RecordRepository recordRepository) {
        this.oilRepository = oilRepository;
        this.recordRepository = recordRepository;
    }

    //TODO: rewrite with rollback
    public boolean storeOil(List<Oil> oilList, User user) {
        var recordId = recordRepository.addRecord(user);
        try {
            oilRepository.addOilList(oilList, recordId);
        } catch (Exception e) {
            e.printStackTrace();
            recordRepository.removeRecord(recordId);
            return false;
        }
        return true;
    }
}
