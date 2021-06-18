package com.artemget.oil_service.datasource;

import com.artemget.oil_service.model.Oil;

import java.util.List;

public interface OilDataSource {
    List<Oil> getAll();

    List<Oil> getLimited();

    //change later
    Oil getBySomeParam(Oil oil);
}
