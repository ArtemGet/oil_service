package com.artemget.oil_service.di.modules;

import com.artemget.oil_service.config.ApplicationConfig;
import com.artemget.oil_service.config.environment.EnvConfig;
import com.artemget.oil_service.exception.ConfigException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class ConfigModule extends AbstractModule {
    @Provides
    @Singleton
    public ApplicationConfig config() {
        try {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            return mapper.readValue(EnvConfig.ENV_CONFIG_YAML.toFile(), ApplicationConfig.class);
        } catch (Exception e) {
            throw new ConfigException(String
                    .format("Error: fail to parse configs from %s, or missing system properties", EnvConfig.ENV_CONFIG_YAML.toString()),
                    e.getCause());
        }
    }
}
