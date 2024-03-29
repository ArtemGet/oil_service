package com.artemget.oil_service.di.modules;

import com.artemget.oil_service.config.ApplicationConfig;
import com.artemget.oil_service.config.environment.EnvConfig;
import com.artemget.oil_service.datasource.*;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jdbi.v3.core.Jdbi;

import static com.artemget.oil_service.config.environment.EnvProperties.POSTGRES;

public class DatasourceModule extends AbstractModule {

    @Override
    public void configure() {
        bind(OilDataSource.class)
                .to(SQLOilSource.class)
                .asEagerSingleton();

        bind(UserDataSource.class)
                .to(SQLUserSource.class)
                .asEagerSingleton();
        bind(RecordDataSource.class)
                .to(SQLRecordDataSource.class)
                .asEagerSingleton();
    }

    @Provides
    @Singleton
    public Jdbi provideJdbiInstance(ApplicationConfig appConfig) {
        if (POSTGRES.getEnvProperty().equals(EnvConfig.DATA_SOURCE)) {
            var hikariConfig = new HikariConfig();
            hikariConfig.setJdbcUrl(appConfig.getSqlConfigMap().get("postgres").getUrl());
            hikariConfig.setPassword(appConfig.getSqlConfigMap().get("postgres").getPassword());
            hikariConfig.setUsername(appConfig.getSqlConfigMap().get("postgres").getUser());
            hikariConfig.setMaximumPoolSize(appConfig.getSqlConfigMap().get("postgres").getPool());
            hikariConfig.setMinimumIdle(2);
            return Jdbi.create(new HikariDataSource(hikariConfig));
        }
        var hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(appConfig.getSqlConfigMap().get("mySQL").getUrl());
        hikariConfig.setPassword(appConfig.getSqlConfigMap().get("mySQL").getPassword());
        hikariConfig.setUsername(appConfig.getSqlConfigMap().get("mySQL").getUser());
        hikariConfig.setMaximumPoolSize(appConfig.getSqlConfigMap().get("mySQL").getPool());
        hikariConfig.setMinimumIdle(2);
        return Jdbi.create(new HikariDataSource(hikariConfig));
    }
}
