package com.artemget.oil_service.e2e;

import io.vertx.ext.web.client.WebClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static com.artemget.oil_service.utils.ExpectedPages.EXPECTED_MAIN_PAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainControllerTest {
    @RegisterExtension
    static E2EExtension extension = new E2EExtension();
    private final WebClient client = extension.client;
    private final String expectedMainPage = EXPECTED_MAIN_PAGE.getPage();

    @Test
    public void shouldSendMainPageByRequest() throws InterruptedException {
        var res = client.get(8080, "localhost", "/")
                .putHeader("content-type", "text/html; charset=utf-8")
                .send();
        while (!res.isComplete()) {
        }
        assertEquals(expectedMainPage, res.result().body().toString());
    }
}
