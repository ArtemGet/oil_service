package com.artemget.oil_service.config.environment;

import lombok.Getter;

@Getter
public enum EnvVariables {

    LOCAL_DEV("local_dev"),
    PRODUCTION("prod"),
    MYSQL("mysql"),
    POSTGRES("postgres");

    private final String envVariable;

    EnvVariables(String envVariable) {
        this.envVariable = envVariable;
    }
}
