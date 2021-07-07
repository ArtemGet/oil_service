package com.artemget.oil_service.utils;

import com.artemget.oil_service.model.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TestUserProvider {
    public static User getCorrectAdmin() {
        return User.builder()
                .name("admin")
                .password("123")
                .email("admin@admin.com")
                .isAdmin(true)
                .build();
    }

    public static User getCorrectUser() {
        return User.builder()
                .name("user")
                .password("123")
                .email("user@user.com")
                .isAdmin(false)
                .build();
    }

    public static User getUserWithCorruptedEmail() {
        return User.builder()
                .name("admin")
                .password("123")
                .email("admin$admincom")
                .isAdmin(true)
                .build();
    }

    public static User getUnregisteredUser() {
        return User.builder()
                .name("unregistered")
                .password("123")
                .email("admin@admin.com")
                .isAdmin(true)
                .build();
    }
}
