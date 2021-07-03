package com.artemget.oil_service.repository;

import com.artemget.oil_service.model.User;

public interface RecordRepository {
    int addRecord(User user);

    void removeRecord(int recordId);
}
