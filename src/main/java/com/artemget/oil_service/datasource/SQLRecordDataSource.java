package com.artemget.oil_service.datasource;

import com.artemget.oil_service.model.Record;
import com.artemget.oil_service.model.User;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.jdbi.v3.core.Jdbi;

import java.time.LocalDateTime;
import java.util.List;

@Singleton
public class SQLRecordDataSource implements RecordDataSource {
    private final Jdbi jdbi;

    @Inject
    public SQLRecordDataSource(Jdbi jdbi) {
        this.jdbi = jdbi;
        jdbi.registerRowMapper(Record.class, (rs, ctx) ->
                Record.builder()
                        .id(rs.getLong("record_id"))
                        .userName(rs.getString("user_name"))
                        .inserted(rs.getLong("inserted"))
                        .corrupted(rs.getLong("corrupted"))
                        .insertionDate(rs.getTimestamp("insertion_date").toLocalDateTime())
                        .build()
        );
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
    public void deleteRecordById(long recordId) {
        jdbi.withHandle(handle ->
                handle.createUpdate("DELETE FROM oil_records WHERE record_id = ?")
                        .bind(0, recordId)
                        .execute());
    }

    @Override
    public void deleteRecordList(List<Long> recordList) {
        System.out.println(recordList.toString());
        jdbi.withHandle(handle -> {
            var batch = handle.prepareBatch("DELETE FROM oil_records WHERE record_id=?");
            recordList.forEach(id -> {
                batch.bind(0, id);
                batch.add();
            });
            batch.execute();
            return null;
        });
    }

    @Override
    public List<Record> selectAllRecords() {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM oil_records")
                        .mapTo(Record.class)
                        .list());
    }
}