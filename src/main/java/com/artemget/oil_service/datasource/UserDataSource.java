package com.artemget.oil_service.datasource;

import com.artemget.oil_service.model.User;

import java.util.List;

public interface UserDataSource {
    void addUser(User user);

    User getUserByNameAndPassword(String name, String password);

    List<User> getUsers();
}
