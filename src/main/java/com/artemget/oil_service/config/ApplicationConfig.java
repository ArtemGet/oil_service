package com.artemget.oil_service.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.inject.Singleton;
import lombok.Getter;
import lombok.NonNull;

import java.util.Map;

@Singleton
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplicationConfig {
    private final Map<String, SQLConfig> sqlConfigMap;

    public ApplicationConfig(
            @NonNull @JsonProperty("postgres") SQLConfig postgresConfig,
            @NonNull @JsonProperty("mySQL") SQLConfig mySQLConfig) {
        this.sqlConfigMap = Map.of("postgres", postgresConfig,
                "mySQL", mySQLConfig);
    }
}
