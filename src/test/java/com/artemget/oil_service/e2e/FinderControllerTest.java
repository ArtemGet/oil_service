package com.artemget.oil_service.e2e;

import com.artemget.oil_service.datasource.OilDataSource;
import com.artemget.oil_service.datasource.UserDataSource;
import com.artemget.oil_service.model.User;
import com.artemget.oil_service.repository.reqest.OilRequest;
import com.artemget.oil_service.utils.TestOilProvider;
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
import static org.mockito.Mockito.when;

public class FinderControllerTest {
    @RegisterExtension
    static E2EExtension extension = new E2EExtension();
    private final WebClient client = extension.client;
    private final UserDataSource userDataSource = extension.mockUserDataSource;
    private final OilDataSource oilDataSource = extension.mockOilDataSource;

    @Test
    public void shouldSendUnauthorizedIfNotLoggedIn() {
        var response = client.request(HttpMethod.GET, 8080, "localhost", "/api/handbooks/handbook/oils/oil?param=density20&value=0.123&limit=1")
                .putHeader("Authorization", "Bearer " + "someInvalidToken")
                .send();
        while (!response.isComplete()) {

        }
        assertEquals(401, response.result().statusCode());
    }

    @Test
    public void shouldSendResultOnSuccess() {
        when(oilDataSource.selectOilList(eq(new OilRequest("density20", 0.123, 1))))
                .thenReturn(List.of(TestOilProvider.provideValidOne()));
        var expected = new JsonObject()
                .put("oils", List.of(TestOilProvider.provideValidOneJson()));
        var resp = sendRequest(client, "density20", 0.123, 1);

        assertEquals(200, resp.result().statusCode());
        assertEquals(expected, resp.result().bodyAsJsonObject());
    }

    @Test
    public void shouldSendNotFoundOnEmptyDB() {
        when(oilDataSource.selectOilList(eq(new OilRequest("density20", 0.123, 1))))
                .thenReturn(List.of());
        var resp = sendRequest(client, "density20", 0.123, 1);

        assertEquals(404, resp.result().statusCode());
    }

    @Test
    public void shouldSendInternalOnLostDBConnection() {
        when(oilDataSource.selectOilList(eq(new OilRequest("density20", 0.123, 1))))
                .thenThrow(RuntimeException.class);
        var resp = sendRequest(client, "density20", 0.123, 1);

        assertEquals(500, resp.result().statusCode());
    }

    @Test
    public void shouldSendBadRequestOnInvalidParams() {
        var resp = sendRequest(client, "density20", -0.123, -1);
        assertEquals(400, resp.result().statusCode());
    }

    public String login(User user) {

        when(userDataSource.selectUserByNameAndPassword(eq(user.getName()), eq(user.getPassword())))
                .thenReturn(user);

        var response = client.request(HttpMethod.GET, 8080, "localhost", "/users/user")
                .sendJson(new JsonObject()
                        .put("name", user.getName())
                        .put("password", user.getPassword()));
        while (!response.isComplete()) {
        }
        return response.result()
                .bodyAsJsonObject()
                .getString("token");
    }

    public Future<HttpResponse<Buffer>> sendRequest(WebClient client, String param, double value, long limit) {
        String token = login(TestUserProvider.getCorrectAdmin());
        String reqPath = String.format("/api/handbooks/handbook/oils/oil?param=%s&value=%f&limit=%d", param, value, limit);

        var response = client.request(HttpMethod.GET, 8080, "localhost", reqPath)
                .putHeader("Authorization", "Bearer " + token)
                .send();
        while (!response.isComplete()) {

        }
        return response;
    }
}
