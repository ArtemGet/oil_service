package com.artemget.oil_service.repository;

import com.artemget.oil_service.datasource.RecordDataSource;
import com.artemget.oil_service.model.Record;
import com.artemget.oil_service.model.User;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.List;

@Singleton
public class RecordRepositoryImpl implements RecordRepository {
    private final RecordDataSource recordDataSource;

    @Inject
    public RecordRepositoryImpl(RecordDataSource recordDataSource) {
        this.recordDataSource = recordDataSource;
    }

    @Override
    public int addRecord(User user, long inserted, long corrupted) {
        return recordDataSource.insertRecord(user.getName(), inserted, corrupted);
    }

    @Override
    public void removeRecord(long recordId) {
        recordDataSource.deleteRecordById(recordId);
    }

    @Override
    public void removeRecords(List<Long> recordIdList) {
recordDataSource.deleteRecordList(recordIdList);
    }

    @Override
    public List<Record> getAll() {
        return recordDataSource.selectAllRecords();
    }
}
