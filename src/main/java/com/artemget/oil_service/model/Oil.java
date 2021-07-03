package com.artemget.oil_service.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class Oil {
    private final String outputPlace;
    private final String name;

    private final double density20;
    private final double density50;
    private final double viscosity20;
    private final double viscosity50;
    private final double HK_350;
}
