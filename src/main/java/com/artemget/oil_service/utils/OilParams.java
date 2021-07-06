package com.artemget.oil_service.utils;

public enum OilParams {
    OUTPUT_FIELD("Область добычи"),
    NAME("Название"),
    P20("p20"),
    P50("p50"),
    V20("v20"),
    V50("v50"),
    HK_350("НК-350");

    private final String content;

    OilParams(String content) {
        this.content = content;
    }

    public static OilParams isValue(String value) {
        for (OilParams param : OilParams.values()) {
            if (param.content.equals(value)) {
                return param;
            }
        }
        return null;
    }
}
