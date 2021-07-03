package com.artemget.oil_service.e2e;

import com.artemget.oil_service.datasource.UserDataSource;
import com.artemget.oil_service.model.User;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.web.client.WebClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;

public class RegistrationControllerTest {
    @RegisterExtension
    static E2EExtension extension = new E2EExtension();
    private final WebClient client = extension.client;
    private final UserDataSource userDataSource = extension.mockUserDataSource;
    private final JWTAuth jwtAuth = extension.mockedJWTAuth;

    @Test
    public void shouldSendTokenOnSuccess() {
        var response = client.request(HttpMethod.POST, 8080, "localhost", "/register")
                .sendJson(new JsonObject()
                        .put("name", "admin")
                        .put("password", "123")
                        .put("email", "admin@admin.com"));

        while (!response.isComplete()) {
        }
        assertEquals(200, response.result().statusCode());

        var userAuth = jwtAuth.authenticate(new JsonObject()
                .put("token",
                        response.result()
                                .bodyAsJsonObject()
                                .getString("token")));
        assertTrue(userAuth.succeeded());
    }

    @Test
    public void shouldSendConflictOnAlreadyExistingUser() {
        doThrow(IllegalStateException.class)
                .when(userDataSource)
                .insertUser(eq(User.builder()
                        .name("takenUserName")
                        .password("123")
                        .email("admin@admin.com")
                        .build()));

        var response = client.request(HttpMethod.POST, 8080, "localhost", "/register")
                .sendJson(new JsonObject()
                        .put("name", "takenUserName")
                        .put("password", "123")
                        .put("email", "admin@admin.com"));
        while (!response.isComplete()) {
        }
        assertEquals(409, response.result().statusCode());
    }

    @Test
    public void shouldSendBadRequestOnFailingValidation() {
        var response = client.request(HttpMethod.POST, 8080, "localhost", "/register")
                .sendJson(new JsonObject()
                        .put("name", "admin")
                        .put("password", "123")
                        .put("email", "email#emailcom"));
        while (!response.isComplete()) {
        }
        assertEquals(400, response.result().statusCode());
    }
}
