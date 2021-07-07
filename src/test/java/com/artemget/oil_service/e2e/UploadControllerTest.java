package com.artemget.oil_service.e2e;

import com.artemget.oil_service.datasource.OilDataSource;
import com.artemget.oil_service.datasource.RecordDataSource;
import com.artemget.oil_service.datasource.UserDataSource;
import com.artemget.oil_service.model.User;
import com.artemget.oil_service.utils.TestOilProvider;
import com.artemget.oil_service.utils.TestUserProvider;
import io.vertx.core.Future;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.multipart.MultipartForm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class UploadControllerTest {
    @RegisterExtension
    static E2EExtension extension = new E2EExtension();
    private final WebClient client = extension.client;
    private final UserDataSource userDataSource = extension.mockUserDataSource;
    private final JWTAuth jwtAuth = extension.mockedJWTAuth;
    private final OilDataSource oilDataSource = extension.mockOilDataSource;
    private final RecordDataSource recordDataSource = extension.mockRecordDataSource;

    @Test
    public void shouldSendUnauthorizedIfNotLoggedIn() {
        MultipartForm form = MultipartForm.create()
                .attribute("fileDescription", "123")
                .binaryFileUpload("file",
                        "valid_single_page.xlsx",
                        "src/test/resources/xlsx_files/valid_single_page.xlsx",
                        "file/xlsx");

        String token = login(TestUserProvider.getCorrectUser());
        var response = client.request(HttpMethod.POST, 8080, "localhost", "/api/handbooks/handbook")
                .putHeader("Authorization", "Bearer " + "someInvalidToken")
                .putHeader("Content-Type", "multipart/form-data")
                .putHeader("Connection", "keep-alive")
                .sendMultipartForm(form);
        while (!response.isComplete()) {

        }
        assertEquals(401, response.result().statusCode());
    }

    @Test
    public void shouldSendForbiddenIfNotAdmin() {
        MultipartForm form = MultipartForm.create()
                .attribute("fileDescription", "123")
                .binaryFileUpload("file",
                        "valid_single_page.xlsx",
                        "src/test/resources/xlsx_files/valid_single_page.xlsx",
                        "file/xlsx");

        String token = login(TestUserProvider.getCorrectUser());
        var response = client.request(HttpMethod.POST, 8080, "localhost", "/api/handbooks/handbook")
                .putHeader("Authorization", "Bearer " + token)
                .putHeader("Content-Type", "multipart/form-data")
                .putHeader("Connection", "keep-alive")
                .sendMultipartForm(form);
        while (!response.isComplete()) {

        }
        assertEquals(403, response.result().statusCode());
    }

    @Test
    public void shouldSendOkOnValidFile() {
        MultipartForm form = MultipartForm.create()
                .attribute("fileDescription", "123")
                .binaryFileUpload("file",
                        "valid_single_page.xlsx",
                        "src/test/resources/xlsx_files/valid_single_page.xlsx",
                        "file/xlsx");

        var response = sendRequest(client, form);
        assertEquals(201, response.result().statusCode());
    }

    @Test
    public void shouldSendConflictOnDBError() {
        MultipartForm form = MultipartForm.create()
                .attribute("fileDescription", "123")
                .binaryFileUpload("file",
                        "valid_single_page.xlsx",
                        "src/test/resources/xlsx_files/valid_single_page.xlsx",
                        "file/xlsx");
        when(recordDataSource.insertRecord("admin"))
                .thenReturn(1);
        doThrow(IllegalStateException.class)
                .when(oilDataSource)
                .insertOilList(List.of(TestOilProvider.provideValidOne(), TestOilProvider.provideValidTwo()), 1);

        var response = sendRequest(client, form);
        assertEquals(409, response.result().statusCode());
    }

    @Test
    public void shouldSendBadRequestOnMoreThanOneFileSent() {
        //two files
        MultipartForm form = MultipartForm.create()
                .attribute("fileDescription", "123")
                .binaryFileUpload("file",
                        "valid_single_page.xlsx",
                        "src/test/resources/xlsx_files/valid_single_page.xlsx",
                        "file/xlsx")
                .binaryFileUpload("file",
                        "valid_single_page.xlsx",
                        "src/test/resources/xlsx_files/valid_single_page.xlsx",
                        "file/xlsx");

        var response = sendRequest(client, form);
        assertEquals(400, response.result().statusCode());
    }

    @Test
    public void shouldSendBadRequestOnInvalidFileFormat() {
        //invalid format
        MultipartForm form = MultipartForm.create()
                .attribute("fileDescription", "123")
                .binaryFileUpload("file",
                        "bib.png",
                        "src/test/resources/xlsx_files/bib.png",
                        "image/png");

        var response = sendRequest(client, form);
        assertEquals(400, response.result().statusCode());
    }

    @Test
    public void shouldSendBadRequestOnInvalidEmptyFile() {
        //empty file
        MultipartForm form = MultipartForm.create();

        var response = sendRequest(client, form);
        assertEquals(400, response.result().statusCode());
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

    public Future<HttpResponse<Buffer>> sendRequest(WebClient client, MultipartForm data) {
        String token = login(TestUserProvider.getCorrectAdmin());

        var response = client.request(HttpMethod.POST, 8080, "localhost", "/api/handbooks/handbook")
                .putHeader("Authorization", "Bearer " + token)
                .putHeader("Content-Type", "multipart/form-data")
                .putHeader("Connection", "keep-alive")
                .sendMultipartForm(data);
        while (!response.isComplete()) {

        }
        return response;
    }
}

