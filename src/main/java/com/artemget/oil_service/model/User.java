package com.artemget.oil_service.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class User {
    private final String name;
    private final String password;
    private final String email;
    private final boolean isAdmin;
}
