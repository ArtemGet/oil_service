package com.artemget.oil_service.repository;

import com.artemget.oil_service.model.User;

public interface UserRepository {
    User getUser(User user);

    void addUser(User user);
}
