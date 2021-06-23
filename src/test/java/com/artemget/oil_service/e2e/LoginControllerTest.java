package com.artemget.oil_service.e2e;

import com.artemget.oil_service.datasource.UserDataSource;
import com.artemget.oil_service.model.User;
import com.artemget.oil_service.utils.TestUserProvider;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.web.client.WebClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class LoginControllerTest {
    @RegisterExtension
    static E2EExtension extension = new E2EExtension();
    private final WebClient client = extension.client;
    private final UserDataSource userDataSource = extension.mockUserDataSource;
    private final JWTAuth jwtAuthProvider = extension.mockedJWTAuth;

    @Test
    public void shouldSendTokenOnSuccess() {
        User correctAdmin = TestUserProvider.getCorrectAdmin();
        String token = "jwt.admin.sign";

        when(userDataSource.getUserByNameAndPassword(eq("admin"), eq("123")))
                .thenReturn(correctAdmin);
        when(jwtAuthProvider.generateToken(new JsonObject()
                .put("name", "admin")
                .put("admin", true)))
                .thenReturn(token);

        var response = client.request(HttpMethod.GET, 8080, "localhost", "/login")
                .sendJson(new JsonObject()
                        .put("name", "admin")
                        .put("password", "123")
                        .put("email", "admin@admin.com"));
        while (!response.isComplete()) {
        }
        assertEquals(token, response.result().bodyAsString());
    }

    @Test
    public void shouldSendNotFoundIfUserNotExists() {
        when(userDataSource.getUserByNameAndPassword(eq("unregisteredUser"), eq("123")))
                .thenThrow(IllegalStateException.class);

        var response = client.request(HttpMethod.GET, 8080, "localhost", "/login")
                .sendJson(new JsonObject()
                        .put("name", "unregisteredUser")
                        .put("password", "123")
                        .put("email", "admin@admin.com"));
        while (!response.isComplete()) {
        }
        assertEquals(404, response.result().statusCode());
    }

    @Test
    public void shouldSendBadRequestOnFailingValidation() {
        var response = client.request(HttpMethod.GET, 8080, "localhost", "/login")
                .sendJson(new JsonObject()
                        .put("name", "user")
                        .put("password", "123")
                        .put("email", "admin$admincom"));
        while (!response.isComplete()) {
        }
        assertEquals(400, response.result().statusCode());
    }
}
