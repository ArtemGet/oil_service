package com.artemget.oil_service.utils;

public enum OilParams {
    OUTPUT_FIELD("Область добычи", "output_place"),
    NAME("Название", "oil_name"),
    P20("p20", "density20"),
    P50("p50", "density50"),
    V20("v20", "viscosity20"),
    V50("v50", "viscosity50"),
    HK_350("НК-350", "HK_350");

    private final String param;
    private final String dBDefinition;

    OilParams(String content, String dBDefinition) {
        this.param = content;
        this.dBDefinition = dBDefinition;
    }

    public static OilParams isValue(String value) {
        for (OilParams param : OilParams.values()) {
            if (param.param.equals(value)) {
                return param;
            }
        }
        return null;
    }

    public static OilParams isDbValue(String value) {
        for (OilParams param : OilParams.values()) {
            if (param.dBDefinition.equals(value)) {
                return param;
            }
        }
        return null;
    }

    public String getDBDefinition() {
        return this.dBDefinition;
    }
}
