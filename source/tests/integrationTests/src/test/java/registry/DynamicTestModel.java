package registry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.ThrowingConsumer;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class DynamicTestModel extends ApplicationTest{
    private static List<String> fileList;
    private static List<Map<String, String>> paramList;
    private static JSONObject model;
    private TestRestTemplate restTemplate = new TestRestTemplate();
    private static Integer id;

    @BeforeAll
    public void environmentPrep() throws IOException {
        paramList = PropConfig.getListProperties();
        fileList = PropConfig.getPropertyFilesList();
    }

    private boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            // if object is not valid then check if JSONArray is valid
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + super.getPort() + uri;
    }

    private ResponseEntity<String> getModel(String modelName) {
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/model/" + modelName), HttpMethod.GET, entity, String.class);
        return response;
    }

    @Order(1)
    @TestFactory
    Stream<DynamicTest> testGetModel() {
        // input generator that generates inputs using inputList
        Iterator<String> inputGenerator = fileList.iterator();

        // a display name generator that creates a
        // different name based on the input
        Function<String, String> displayNameGenerator = (input) -> "Resolving: " + input;

        // the test executor, which actually has the
        // logic to execute the test case
        ThrowingConsumer<String> testExecutor = (input) -> {
            int id = fileList.indexOf(input);
            Map<String, String> param = paramList.get(id);
            ResponseEntity<String> response = getModel(param.get(input));
            assertEquals(response.getStatusCodeValue(), 200);
            Assertions.assertTrue(isJSONValid(response.getBody()));

        };

        // combine everything and return a Stream of DynamicTest
        return DynamicTest.stream(
                inputGenerator, displayNameGenerator, testExecutor);
    }

}
