package com.artemget.oil_service.repository;

import com.artemget.oil_service.model.Oil;

import java.util.List;

public interface OilRepository {
    List<Oil> getAll();

    Oil getBySomeParam(Oil oil);

    void addOilList(List<Oil> oilList, int recordId);
}
