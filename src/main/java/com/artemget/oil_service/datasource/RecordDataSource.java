package com.artemget.oil_service.datasource;

import com.artemget.oil_service.model.Record;

import java.util.List;

public interface RecordDataSource {
    int insertRecord(String adminName, long inserted, long corrupted);

    void deleteRecordById(long recordId);

    void deleteRecordList(List<Record> recordList);

    List<Record> selectAllRecords();
}
