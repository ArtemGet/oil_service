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
    public List<Oil> getAll() {
        return null;
    }

    @Override
    public List<Oil> getLimited() {
        return null;
    }

    @Override
    public Oil getBySomeParam(Oil oil) {
        return null;
    }
}
