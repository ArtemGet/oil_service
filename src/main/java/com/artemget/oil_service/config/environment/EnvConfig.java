package com.artemget.oil_service.config.environment;

import java.nio.file.Path;
import java.util.Locale;

public class EnvConfig {
    public static final String ENVIRONMENT = getEnvironment();
    public static final String DATA_SOURCE = getDataSource();

    public static final Path ENV_CONFIG_YAML = Path.of("src/main/resources/config/", "config." + ENVIRONMENT + ".yaml");

    public static String getEnvironment() {
        var env = System.getProperty("ENV");
        try {
            EnvProperties.valueOf(env.toUpperCase());
        } catch (Exception e) {
            return EnvProperties.LOCAL_DEV.toString().toLowerCase(Locale.ROOT);
        }
        return env;
    }

    public static String getDataSource() {
        var env = System.getProperty("dataSource");
        try {
            EnvProperties.valueOf(env.toUpperCase(Locale.ROOT));
        } catch (Exception e) {
            return EnvProperties.MYSQL.getEnvProperty();
        }
        return env;
    }

    public static String getKeyStorePath() {
        return System.getProperty("keyStorePath");
    }

    public static String getSecretBuffer() {
        return System.getProperty("secretBuffer");
    }

    public static String getKeyStorePassword() {
        return System.getProperty("keyStorePassword");
    }
}
