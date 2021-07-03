package com.artemget.oil_service.datasource;

import com.artemget.oil_service.model.Oil;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

@Singleton
public class SQLOilSource implements OilDataSource {
    private final Jdbi jdbi;

    @Inject
    public SQLOilSource(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @Override
    public List<Oil> selectAll() {
        return null;
    }

    @Override
    public List<Oil> selectOilLimited(int limit) {
        return null;
    }

    @Override
    public void insertOilList(List<Oil> oilList, int recordId) {
        jdbi.withHandle((handle ->
        {
            var batch = handle.prepareBatch("INSERT INTO oil_types " +
                    "(output_place, oil_name, density20, density50, viscosity20, viscosity50, HK_350, record_id )" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            for (Oil oil : oilList) {
                batch.bind(0, oil.getOutputPlace())
                        .bind(1, oil.getName())
                        .bind(2, oil.getDensity20())
                .bind(3, oil.getDensity50())
                .bind(4, oil.getViscosity20())
                .bind(5, oil.getViscosity50())
                .bind(6, oil.getHK_350())
                .bind(7, recordId);
            }
            batch.execute();
            return null;
        }
        ));
    }

    @Override
    public Oil selectBySomeParam(Oil oil) {
        return null;
    }
}
