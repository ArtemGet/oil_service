package com.artemget.oil_service.datasource;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.jdbi.v3.core.Jdbi;

import java.time.LocalDateTime;

@Singleton
public class SQLRecordDataSource implements RecordDataSource {
    private final Jdbi jdbi;

    @Inject
    public SQLRecordDataSource(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @Override
    public int insertRecord(String adminName, long inserted, long corrupted) {
        return jdbi.withHandle(handle ->
                handle.createUpdate("INSERT INTO oil_records (user_name, inserted, corrupted, insertion_date) VALUES (?,?,?,?)")
                        .bind(0, adminName)
                        .bind(1, inserted)
                        .bind(2, corrupted)
                        .bind(3, LocalDateTime.now())
                        .executeAndReturnGeneratedKeys("record_id")
                        .mapTo(Integer.class)
                        .first()
        );
    }

    @Override
    public void deleteRecordById(int recordId) {
        jdbi.withHandle(handle ->
                handle.createUpdate("DELETE FROM oil_records WHERE record_id = ?")
                        .bind(0, recordId)
                        .execute());
    }
}
