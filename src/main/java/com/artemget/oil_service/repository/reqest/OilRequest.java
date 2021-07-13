package com.artemget.oil_service.repository.reqest;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class OilRequest {
    private final String param;
    private final double value;
    private final long limit;
}
