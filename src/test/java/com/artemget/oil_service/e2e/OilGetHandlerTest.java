package com.artemget.oil_service.e2e;

import com.artemget.oil_service.datasource.OilDataSource;
import com.artemget.oil_service.datasource.RecordDataSource;
import com.artemget.oil_service.datasource.UserDataSource;
import com.artemget.oil_service.model.User;
import com.artemget.oil_service.utils.TestOilProvider;
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

public class OilGetHandlerTest {
    @RegisterExtension
    static E2EExtension extension = new E2EExtension();
    private final WebClient client = extension.client;
    private final RecordDataSource recordDataSource = extension.mockRecordDataSource;
    private final OilDataSource oilDataSource = extension.mockOilDataSource;
    private final UserDataSource userDataSource = extension.mockUserDataSource;

    @Test
    public void onSuccess() {
        var expected = new JsonObject().put("oils",
                List.of(TestOilProvider.provideValidOneJson(), TestOilProvider.provideValidTwoJson()));
        when(oilDataSource.selectOilListByRecordId(1))
                .thenReturn(List.of(TestOilProvider.provideValidOne(), TestOilProvider.provideValidTwo()));
        var res = sendRequest(client, 1L);
        assertEquals(200, res.result().statusCode());
        assertEquals(expected, res.result().bodyAsJsonObject());
        reset(oilDataSource);
    }

    @Test
    public void badRequest() {
        var res = sendRequest(client, null);
        assertEquals(400, res.result().statusCode());
    }

    @Test
    public void notFoundOnEmptyRecord() {
        when(oilDataSource.selectOilListByRecordId(1))
                .thenReturn(List.of());
        var res = sendRequest(client, 1L);
        assertEquals(404, res.result().statusCode());
        reset(oilDataSource);
    }

    @Test
    public void internalOnDbError() {
        when(oilDataSource.selectOilListByRecordId(1))
                .thenThrow(RuntimeException.class);
        var res = sendRequest(client, 1L);
        assertEquals(500, res.result().statusCode());
        reset(oilDataSource);
    }

    @Test
    public void internalOnRecordDbError() {
        when(oilDataSource.selectOilListByRecordId(1))
                .thenReturn(List.of());
        doThrow(RuntimeException.class)
                .when(recordDataSource)
                .deleteRecordById(1);
        var res = sendRequest(client, 1L);
        assertEquals(500, res.result().statusCode());
        reset(oilDataSource);
        reset(recordDataSource);
    }

    public Future<HttpResponse<Buffer>> sendRequest(WebClient client, Long recordId) {
        return sendRequest(client, recordId, TestUserProvider.getCorrectAdmin());
    }

    public Future<HttpResponse<Buffer>> sendRequest(WebClient client, Long recordId, User user) {
        String token = login(user);

        var response = client.request(HttpMethod.GET, 8080, "localhost", String.format("/api/oils/%d", recordId))
                .putHeader("Authorization", "Bearer " + token)
                .putHeader("Accept", "application/json")
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
