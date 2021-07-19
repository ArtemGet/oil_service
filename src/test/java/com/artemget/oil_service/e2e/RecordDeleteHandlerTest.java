package com.artemget.oil_service.e2e;

import com.artemget.oil_service.datasource.OilDataSource;
import com.artemget.oil_service.datasource.RecordDataSource;
import com.artemget.oil_service.datasource.UserDataSource;
import com.artemget.oil_service.model.User;
import com.artemget.oil_service.utils.TestUserProvider;
import io.vertx.core.Future;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class RecordDeleteHandlerTest {
    @RegisterExtension
    static E2EExtension extension = new E2EExtension();
    private final WebClient client = extension.client;
    private final RecordDataSource recordDataSource = extension.mockRecordDataSource;
    private final OilDataSource oilDataSource = extension.mockOilDataSource;
    private final UserDataSource userDataSource = extension.mockUserDataSource;

    @Test
    public void onSuccess() {
        var res = sendRequest(client);
        assertEquals(201, res.result().statusCode());
    }

    @Test
    public void internalOnDBError() {
        doThrow(RuntimeException.class)
                .when(recordDataSource)
                .deleteRecordList(List.of(1L, 2L, 3L));
        var res = sendRequest(client);
        assertEquals(500, res.result().statusCode());
        reset(recordDataSource);
    }

    @Test
    public void failOnUserRoots() {
        var res = sendRequest(client, TestUserProvider.getCorrectUser(), List.of(1L, 2L, 3L));
        assertEquals(403, res.result().statusCode());
    }

    @Test
    public void failOnValidationFailed() {
        var res = sendRequest(client, TestUserProvider.getCorrectUser(), List.of("one", "two"));
        assertEquals(400, res.result().statusCode());
    }

    public Future<HttpResponse<Buffer>> sendRequest(WebClient client) {
        return sendRequest(client, TestUserProvider.getCorrectAdmin(), List.of(1L, 2L, 3L));
    }

    public Future<HttpResponse<Buffer>> sendRequest(WebClient client, User user, List idList) {
        String token = login(user);

        var response = client.request(HttpMethod.DELETE, 8080, "localhost", "/api/records")
                .putHeader("Authorization", "Bearer " + token)
                .putHeader("Content-Type", "application/json")
                .sendJson(new JsonObject().put("record_id_array", idList));
        while (!response.isComplete()) {

        }
        return response;
    }

    public String login(User user) {
        when(userDataSource.selectUserByNameAndPassword(eq(user.getName()), eq(user.getPassword())))
                .thenReturn(user);

        var response = client.request(HttpMethod.GET, 8080, "localhost", String.format("/users/%s", user.getName()))
                .sendJson(new JsonObject()
                        .put("password", user.getPassword()));
        while (!response.isComplete()) {
        }
        return response.result()
                .bodyAsJsonObject()
                .getString("token");
    }
}
