package registry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.ThrowingConsumer;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;


public class DynamicTestModel extends ApplicationTest{
    private static List<String> fileList;
    private static List<Map<String, String>> paramList;
    private TestRestTemplate restTemplate = new TestRestTemplate();
    private static Map<String, Integer> idMap = new HashMap<>();

    @BeforeAll
    public static void environmentPrep() throws IOException {
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
            ResponseEntity<String> response = getModel(input);
            assertEquals(200, response.getStatusCodeValue());
            Assertions.assertTrue(isJSONValid(response.getBody()));
            System.out.println("body: " + response.getBody());

        };

        // combine everything and return a Stream of DynamicTest
        return DynamicTest.stream(inputGenerator, displayNameGenerator, testExecutor);
    }

    @Order(2)
    @TestFactory
    Stream<DynamicTest> testInsertModel() {Iterator<String> inputGenerator = fileList.iterator();

        Function<String, String> displayNameGenerator = (input) -> "Resolving: " + input;

        ThrowingConsumer<String> testExecutor = (input) -> {
            int id = fileList.indexOf(input);
            Map<String, String> param = paramList.get(id);
            JSONObject model = new JSONObject(param.get("modelString"));
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            HttpEntity<String> entity = new HttpEntity<String>(model.toString(), headers);
            ResponseEntity<String> response = this.restTemplate.exchange(
                    createURLWithPort("/api/model/" + input), HttpMethod.POST, entity, String.class);
            assertEquals(response.getStatusCodeValue(), 200);
            String responseString = response.getBody();
            Assertions.assertTrue(responseString.contains(param.get("responseBodyBeforeUpdate")));

            ResponseEntity<String> newResponse = getModel(input);
            assertEquals(newResponse.getStatusCodeValue(), 200);
            Assertions.assertTrue(newResponse.getBody().contains(param.get("responseBodyBeforeUpdate")));
            id = Integer.valueOf(responseString.split(",")[0].substring(12));
            idMap.put(input, id);

        };

        return DynamicTest.stream(
                inputGenerator, displayNameGenerator, testExecutor);
    }

    @Order(3)
    @TestFactory
    Stream<DynamicTest> testUpdateModel() {Iterator<String> inputGenerator = fileList.iterator();

        Function<String, String> displayNameGenerator = (input) -> "Resolving: " + input;

        ThrowingConsumer<String> testExecutor = (input) -> {
            int id = fileList.indexOf(input);
            Map<String, String> param = paramList.get(id);
            JSONObject model = new JSONObject(param.get("modelStringToUpdate"));
            model.put(param.get("idName"), idMap.get(input));
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            HttpEntity<String> entity = new HttpEntity<String>(model.toString(), headers);
            ResponseEntity<String> response = this.restTemplate.exchange(
                    createURLWithPort("/api/model/" + input), HttpMethod.PUT, entity, String.class);
            assertEquals(200, response.getStatusCodeValue());
            String responseString = response.getBody();
            System.out.println("body: " + response.getBody());
            Assertions.assertTrue(responseString.contains(param.get("responseBodyAfterUpdate")));

            ResponseEntity<String> newResponse = getModel(input);
            assertEquals(200, newResponse.getStatusCodeValue());
            Assertions.assertTrue(newResponse.getBody().contains(param.get("responseBodyAfterUpdate")));
        };
        return DynamicTest.stream(inputGenerator, displayNameGenerator, testExecutor);
    }

    @Order(4)
    @TestFactory
    Stream<DynamicTest> testDeleteModel() {Iterator<String> inputGenerator = fileList.iterator();

        Function<String, String> displayNameGenerator = (input) -> "Resolving: " + input;

        ThrowingConsumer<String> testExecutor = (input) -> {
            int id = fileList.indexOf(input);
            Map<String, String> param = paramList.get(id);
            JSONObject body = new JSONObject();
            body.put("ID_PERSON", idMap.get(input));
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            HttpEntity<String> entity = new HttpEntity<String>(body.toString(), headers);
            ResponseEntity<String> response = this.restTemplate.exchange(
                    createURLWithPort("/api/model/" + input), HttpMethod.DELETE, entity, String.class);
            assertEquals(200, response.getStatusCodeValue());

            ResponseEntity<String> newResponse = getModel(input);
            assertEquals(200, newResponse.getStatusCodeValue());
            Assertions.assertFalse(newResponse.getBody().contains(param.get("responseBodyAfterUpdate")));
        };
        return DynamicTest.stream(inputGenerator, displayNameGenerator, testExecutor);
    }




}
