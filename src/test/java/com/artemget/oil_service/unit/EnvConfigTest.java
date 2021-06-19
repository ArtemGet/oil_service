package com.artemget.oil_service.unit;

import com.artemget.oil_service.config.environment.EnvConfig;
import com.artemget.oil_service.config.environment.EnvProperties;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnvConfigTest {

    @Test
    public void shouldSetDefaultEnvOnMissingEnvProperty() {
        System.setProperty("ENV","");
        assertEquals(EnvProperties.LOCAL_DEV.getEnvProperty(),EnvConfig.getEnvironment());
    }

    @Test
    public void shouldSetExistingEnvProperty() {
        System.setProperty("ENV", "production");
        assertEquals(EnvProperties.PRODUCTION.getEnvProperty(), EnvConfig.getEnvironment());
    }

    @Test
    public void shouldSetDefaultDataSourceOnMissingDataSource() {
        System.setProperty("DATASOURCE", "");
        assertEquals(EnvProperties.MYSQL.getEnvProperty(), EnvConfig.getDataSource());
    }

    @Test
    public void shouldSetExistingDataSource() {
        System.setProperty("DATASOURCE", "postgres");
        assertEquals(EnvProperties.POSTGRES.getEnvProperty(), EnvConfig.getDataSource());
    }
}