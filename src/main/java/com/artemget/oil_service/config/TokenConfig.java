package com.artemget.oil_service.config;

import com.google.inject.Singleton;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Singleton
@Getter
@AllArgsConstructor
public class TokenConfig {
    @NonNull
    private final String keyStorePath;
    @NonNull
    private final String keyStorePassword;
    @NonNull
    private final String secretBuffer;
}