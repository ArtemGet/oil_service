package com.artemget.oil_service.utils;

import com.artemget.oil_service.model.Oil;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TestOilProvider {
    public static Oil provideValidOne() {
        return Oil.builder()
                .name("Шляховская-3")
                .outputPlace("Волгоградская область")
                .density20(798.0E-3)
                .density50(798.0E-3)
                .viscosity20(3.17)
                .viscosity50(1.9)
                .hk350(63.8)
                .build();
    }

    public static Oil provideValidTwo() {
        return Oil.builder()
                .name("Антиповско-балыклейская")
                .outputPlace("Волгоградская область")
                .density20(814.2E-3)
                .density50(814.2E-3)
                .viscosity20(3.94)
                .viscosity50(2.24)
                .hk350(65.9)
                .build();
    }

    public static Oil provideValidThree() {
        return Oil.builder()
                .name("Коробковская-2")
                .outputPlace("Волгоградская область")
                .density20(818.2E-3)
                .density50(818.2E-3)
                .viscosity20(5.14)
                .viscosity50(2.59)
                .hk350(63.1)
                .build();
    }
}
