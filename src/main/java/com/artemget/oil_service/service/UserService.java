package com.artemget.oil_service.service;

import com.artemget.oil_service.model.User;
import com.artemget.oil_service.repository.UserRepository;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class UserService {
    private final UserRepository userRepository;

    @Inject
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(User user) {
        userRepository.addUser(user);
    }

    public boolean isAdmin(User user) {
        return userRepository.getUser(user).isAdmin();
    }
}
