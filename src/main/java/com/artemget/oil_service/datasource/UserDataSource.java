package com.artemget.oil_service.datasource;

import com.artemget.oil_service.model.User;

import java.util.List;

public interface UserDataSource {
    void insertUser(User user);

    User selectUserByNameAndPassword(String name, String password);

    List<User> selectAllUsers();
}
