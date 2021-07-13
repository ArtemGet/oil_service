package com.artemget.oil_service.di.modules;

import com.artemget.oil_service.validation.*;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

public class ValidatorModule extends AbstractModule {
    @Provides
    @Named("registration_validator")
    @Singleton
    public HttpValidator provideRegistrationValidator() {
        return new RegistrationValidator();
    }

    @Provides
    @Named("login_validator")
    @Singleton
    public HttpValidator provideLoginValidator() {
        return new LoginValidator();
    }

    @Provides
    @Named("upload_validator")
    @Singleton
    public HttpValidator provideUploadValidator() {
        return new UploadValidator();
    }

    @Provides
    @Named("finder_validator")
    @Singleton
    public HttpValidator provideFinderValidator() {
        return new FinderValidator();
    }

}
