package com.artemget.oil_service.datasource;

import com.artemget.oil_service.model.Oil;
import com.artemget.oil_service.model.OilRequest;

import java.util.List;

public interface OilDataSource {
    List<Oil> selectAll();

    List<Oil> selectOilLimited(int limit);

    void insertOilList(List<Oil> oilList, int recordId);

    List<Oil> selectOilList(OilRequest requestList);
}
