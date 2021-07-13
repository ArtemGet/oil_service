package com.artemget.oil_service.repository;

import com.artemget.oil_service.datasource.OilDataSource;
import com.artemget.oil_service.model.Oil;
import com.artemget.oil_service.repository.reqest.OilRequest;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.List;

@Singleton
public class OilRepositoryImpl implements OilRepository {
    private final OilDataSource dataSource;

    @Inject
    public OilRepositoryImpl(OilDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Oil> getAll() {
        return null;
    }

    @Override
    public List<Oil> getOils(OilRequest oilRequest) {
        return dataSource.selectOilList(oilRequest);
    }

    @Override
    public void addOilList(List<Oil> oilList, int recordId) {
        dataSource.insertOilList(oilList, recordId);
    }
}
