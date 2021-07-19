package com.artemget.oil_service.repository;

import com.artemget.oil_service.model.Oil;
import com.artemget.oil_service.repository.reqest.OilRequest;

import java.util.List;

public interface OilRepository {
    List<Oil> getAll();

    List<Oil> getOils(OilRequest oilRequest);

    List<Oil> getOilsByRecord(long recordId);

    void addOilList(List<Oil> oilList, int recordId);
}
