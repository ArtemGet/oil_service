package com.artemget.oil_service.di;

import com.artemget.oil_service.exception.ConfigException;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Getter
public class ApplicationDI {
    private final Injector injector;

    private ApplicationDI(@NonNull Injector injector) {
        this.injector = injector;
    }

    public static DIBuilder newBuilder() {
        return new DIBuilder();
    }

    @Slf4j
    public static class DIBuilder {
        private List<Module> modules;

        public DIBuilder addModules(Module... modules) {
            this.modules = Arrays.asList(modules);
            return this;
        }

        public ApplicationDI build() {
            try {
                return new ApplicationDI(Guice.createInjector(modules));
            } catch (ConfigException e) {
                log.error(e.getMessage(), e);
                return null;
            }
        }
    }
}
