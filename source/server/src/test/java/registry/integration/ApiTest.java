package registry.integration;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.server.LocalServerPort;
import registry.utils.RunServer;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunServer
public class ApiTest {
    @LocalServerPort
    private int randomServerPort;

    private CloseableHttpClient httpClient = HttpClients.createDefault();

    @Test
    public void hiTest() throws IOException {
        HttpGet request = new HttpGet(String.format("http://localhost:%d/api/hi", randomServerPort));
        try (CloseableHttpResponse response = httpClient.execute(request)) {

            assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());

            HttpEntity entity = response.getEntity();
            assertEquals("hi", EntityUtils.toString(entity));
        }
    }
}
