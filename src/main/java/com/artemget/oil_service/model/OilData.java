package com.artemget.oil_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class OilData {
    private final List<Oil> oilList;
    private final long corrupted;
}
