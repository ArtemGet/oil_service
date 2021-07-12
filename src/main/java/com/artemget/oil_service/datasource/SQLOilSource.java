package com.artemget.oil_service.datasource;

import com.artemget.oil_service.model.Oil;
import com.artemget.oil_service.model.OilRequest;
import com.artemget.oil_service.model.User;
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
        jdbi.registerRowMapper(Oil.class, (rs, ctx) ->
                Oil.builder()
                        .name(rs.getString("oil_name"))
                        .outputPlace(rs.getString("output_place"))
                        .density20(rs.getDouble("density20"))
                        .density50(rs.getDouble("density50"))
                        .viscosity20(rs.getDouble("viscosity20"))
                        .density50(rs.getDouble("viscosity50"))
                        .hk350(rs.getDouble("HK_350"))
                        .build()
        );
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
                        .bind(6, oil.getHk350())
                        .bind(7, recordId)
                        .add();
            }
            batch.execute();
            return null;
        }
        ));
    }

    //pattern!!!!
    @Override
    public List<Oil> selectOilList(OilRequest request) {
       return jdbi.withHandle(handle ->
                handle.createQuery("SELECT FROM * " +
                        "((SELECT * FROM oil_types WHERE :param >= :value ORDER BY density20 LIMIT :limit) " +
                        "UNION ALL " +
                        "(SELECT * FROM oil_types WHERE :param < :value ORDER BY density20 DESC LIMIT :limit) " +
                        ") as r*l* " +
                        "ORDER BY abs(:param - :value) LIMIT = :limit")
                        .bind("param", request.getParam())
                        .bind("value", request.getValue())
                        .bind("limit", request.getLimit())
                        .mapTo(Oil.class)
                        .list()
        );
    }
}
