package com.artemget.oil_service.repository;

import com.artemget.oil_service.model.User;

public interface RecordRepository {
    int addRecord(User user, long inserted, long corrupted);

    void removeRecord(int recordId);
}
