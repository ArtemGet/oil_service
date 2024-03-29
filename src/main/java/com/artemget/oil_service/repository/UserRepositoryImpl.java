package com.artemget.oil_service.repository;

import com.artemget.oil_service.datasource.UserDataSource;
import com.artemget.oil_service.model.User;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class UserRepositoryImpl implements UserRepository {
    private final UserDataSource userDataSource;

    @Inject
    public UserRepositoryImpl(UserDataSource userDataSource) {
        this.userDataSource = userDataSource;
    }

    @Override
    public User getUser(User user) {
        return userDataSource.selectUserByNameAndPassword(user.getName(), user.getPassword());
    }

    @Override
    public void addUser(User user) {
        userDataSource.insertUser(user);
    }
}
