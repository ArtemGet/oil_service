package com.artemget.oil_service.datasource;

public interface RecordDataSource {
    int insertRecord(String adminName, long inserted, long corrupted);

    void deleteRecordById(int recordId);
}
