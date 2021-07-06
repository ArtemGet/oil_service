package com.artemget.oil_service.datasource;

import com.artemget.oil_service.model.User;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

@Singleton
public class SQLUserSource implements UserDataSource {
    private final Jdbi jdbi;

    @Inject
    public SQLUserSource(Jdbi jdbi) {
        this.jdbi = jdbi;
        jdbi.registerRowMapper(User.class, (rs, ctx) ->
                User.builder()
                        .name(rs.getString("user_name"))
                        .password(rs.getString("password"))
                        .email(rs.getString("email"))
                        .isAdmin(rs.getBoolean("is_admin"))
                        .build()
        );
    }

    @Override
    public void insertUser(User user) {
        jdbi.withHandle(handle ->
                handle.createUpdate("INSERT INTO users (email, user_name, password) VALUES (?, ?, ?)")
                        .bind(0, user.getEmail())
                        .bind(1, user.getName())
                        .bind(2, user.getPassword())
                        .execute());
    }

    @Override
    public User selectUserByNameAndPassword(String name, String password) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM users WHERE user_name = ? AND password = ?")
                        .bind(0, name)
                        .bind(1, password)
                        .mapTo(User.class)
                        .one()
        );
    }

    @Override
    public List<User> selectAllUsers() {
        return null;
    }
}
