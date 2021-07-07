package com.artemget.oil_service.datasource;

import com.artemget.oil_service.model.Oil;

import java.util.List;

public interface OilDataSource {
    List<Oil> selectAll();

    List<Oil> selectOilLimited(int limit);

    void insertOilList(List<Oil> oilList, int recordId);

    //change later
    Oil selectBySomeParam(Oil oil);
}
