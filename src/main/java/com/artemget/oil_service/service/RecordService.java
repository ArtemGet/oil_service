package com.artemget.oil_service.service;

import com.artemget.oil_service.model.Record;
import com.artemget.oil_service.repository.OilRepository;
import com.artemget.oil_service.repository.RecordRepository;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.List;

@Singleton
public class RecordService {
    private final RecordRepository recordRepository;

    @Inject
    public RecordService(OilRepository oilRepository, RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    public List<Record> getAllRecords() {
        return recordRepository.getAll();
    }

    public void clearRecords(List<Long> recordList) {

    }

    public void clearRecord(long recordList) {
        recordRepository.removeRecord(recordList);
    }
}
