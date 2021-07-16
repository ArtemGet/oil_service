package com.artemget.oil_service.service;

import com.artemget.oil_service.model.Oil;
import com.artemget.oil_service.model.OilData;
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
    public boolean storeOil(OilData oilData, User user) {
        var recordId = recordRepository.addRecord(user, oilData.getOilList().size(), oilData.getCorrupted());
        try {
            oilRepository.addOilList(oilData.getOilList(), recordId);
        } catch (Exception e) {
            e.printStackTrace();
            recordRepository.removeRecord(recordId);
            return false;
        }
        return true;
    }
}
