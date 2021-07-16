package com.artemget.oil_service.repository;

import com.artemget.oil_service.model.Record;
import com.artemget.oil_service.model.User;

import java.util.List;

public interface RecordRepository {
    int addRecord(User user, long inserted, long corrupted);

    void removeRecord(int recordId);

    void removeRecords(List<Record> recordIdList);

    List<Record> getAll();
}
