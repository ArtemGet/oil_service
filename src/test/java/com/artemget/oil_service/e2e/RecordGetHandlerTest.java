package com.artemget.oil_service.e2e;

import com.artemget.oil_service.datasource.RecordDataSource;
import com.artemget.oil_service.datasource.UserDataSource;
import com.artemget.oil_service.model.User;
import com.artemget.oil_service.utils.TestRecordProvider;
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

public class RecordGetHandlerTest {
    @RegisterExtension
    static E2EExtension extension = new E2EExtension();
    private final WebClient client = extension.client;
    private final RecordDataSource recordDataSource = extension.mockRecordDataSource;
    private final UserDataSource userDataSource = extension.mockUserDataSource;

    @Test
    public void forbiddenOnUserRoots() {
        var resp = sendRequest(client, TestUserProvider.getCorrectUser());
        assertEquals(403, resp.result().statusCode());
    }

    @Test
    public void onSuccess() {
        when(recordDataSource.selectAllRecords())
                .thenReturn(List.of(TestRecordProvider.provideValidRecordOne(), TestRecordProvider.provideValidRecordTwo()));
        var expected = new JsonObject().put("records",
                List.of(TestRecordProvider.provideValidRecordOneJson(),
                        TestRecordProvider.provideValidRecordTwoJson()));

        var resp = sendRequest(client);
        assertEquals(200, resp.result().statusCode());
        assertEquals(expected, resp.result().bodyAsJsonObject());
    }

    @Test
    public void notFoundOnEmptyTable() {
        when(recordDataSource.selectAllRecords())
                .thenReturn(List.of());

        var resp = sendRequest(client);
        assertEquals(404, resp.result().statusCode());
    }

    @Test
    public void internalOnDbError() {
       var a = when(recordDataSource.selectAllRecords())
                .thenThrow(RuntimeException.class);

        var resp = sendRequest(client);
        assertEquals(500, resp.result().statusCode());
        reset(recordDataSource);
    }

    public Future<HttpResponse<Buffer>> sendRequest(WebClient client) {
        return sendRequest(client, TestUserProvider.getCorrectAdmin());
    }

    public Future<HttpResponse<Buffer>> sendRequest(WebClient client, User user) {
        String token = login(user);

        var response = client.request(HttpMethod.GET, 8080, "localhost", "/api/records")
                .putHeader("Authorization", "Bearer " + token)
                .putHeader("Content-Type", "application/json")
                .send();
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
