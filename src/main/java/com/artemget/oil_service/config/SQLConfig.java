package com.artemget.oil_service.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.inject.Singleton;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

@Singleton
@Getter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class SQLConfig {
    private final String url;
    private final String user;
    private final String password;
    private final Integer pool;

    public SQLConfig(
            @NonNull @JsonProperty("url") String url,
            @NonNull @JsonProperty("user") String user,
            @NonNull @JsonProperty("password") String password,
            @NonNull @JsonProperty("pool") Integer pool) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.pool = pool;
    }
}
