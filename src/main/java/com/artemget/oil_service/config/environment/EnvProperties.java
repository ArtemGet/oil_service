package com.artemget.oil_service.config.environment;

import lombok.Getter;

@Getter
public enum EnvProperties {

    LOCAL_DEV("local_dev"),
    PRODUCTION("production"),
    MYSQL("mysql"),
    POSTGRES("postgres");

    private final String envProperty;

    EnvProperties(String envProperty) {
        this.envProperty = envProperty;
    }
}
