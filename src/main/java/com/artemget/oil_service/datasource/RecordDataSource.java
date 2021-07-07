package com.artemget.oil_service.datasource;

public interface RecordDataSource {
    int insertRecord(String adminName);

    void deleteRecordById(int recordId);
}
