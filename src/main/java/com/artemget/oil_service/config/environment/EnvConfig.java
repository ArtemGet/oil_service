package com.artemget.oil_service.config.environment;

import java.nio.file.Path;
import java.util.Locale;

public class EnvConfig {
    public static final String ENVIRONMENT = getEnvironment();
    public static final String DATA_SOURCE = getDataSource();

    public static final Path ENV_CONFIG_YAML = Path.of("src/main/resources/config/", "config." + ENVIRONMENT + ".yaml");

    private static String getEnvironment() {
        var env = System.getenv("ENV");
        try {
            EnvVariables.valueOf(env.toUpperCase());
        } catch (Exception e) {
            return EnvVariables.LOCAL_DEV.toString().toLowerCase(Locale.ROOT);
        }
        return env;
    }

    private static String getDataSource() {
        var env = System.getenv("DATASOURCE");
        try {
            EnvVariables.valueOf(env.toUpperCase(Locale.ROOT));
        } catch (Exception e) {
            return EnvVariables.MYSQL.getEnvVariable();
        }
        return env;
    }
}
