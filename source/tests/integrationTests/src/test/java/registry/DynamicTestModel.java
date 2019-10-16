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
    private static final String uri = "/api/model/";
    private static final String host = "http://localhost:";
    private static final String displayName = "Resolving: ";
    private static final int expectedResponseCode = 200;

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
        return host + super.getPort() + uri;
    }

    private ResponseEntity<String> getModel(String modelName) {
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort(uri + modelName), HttpMethod.GET, entity, String.class);
        return response;
    }

    @Order(1)
    @TestFactory
    Stream<DynamicTest> testGetModel() {
        return fileList.stream().map(element -> DynamicTest.dynamicTest(displayName + element,
                () -> {
                    ResponseEntity<String> response = getModel(element);
                    assertEquals(expectedResponseCode, response.getStatusCodeValue());
                    Assertions.assertTrue(isJSONValid(response.getBody()));
                }));
    }

    @Order(2)
    @TestFactory
    Stream<DynamicTest> testInsertModel() {Iterator<String> inputGenerator = fileList.iterator();
        return fileList.stream().map(element -> DynamicTest.dynamicTest(displayName + element,
                () -> {
                    int id = fileList.indexOf(element);
                    Map<String, String> param = paramList.get(id);
                    JSONObject model = new JSONObject(param.get("modelString"));
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
                    HttpEntity<String> entity = new HttpEntity<String>(model.toString(), headers);
                    ResponseEntity<String> response = this.restTemplate.exchange(
                            createURLWithPort(uri + element), HttpMethod.POST, entity, String.class);
                    assertEquals(expectedResponseCode, response.getStatusCodeValue());
                    String responseString = response.getBody();
                    Assertions.assertTrue(responseString.contains(param.get("responseBodyBeforeUpdate")));

                    ResponseEntity<String> newResponse = getModel(element);
                    assertEquals(expectedResponseCode, newResponse.getStatusCodeValue());
                    Assertions.assertTrue(newResponse.getBody().contains(param.get("responseBodyBeforeUpdate")));
                    idMap.put(element, Integer.valueOf(responseString.split(",")[0].substring(12)));
                }));
    }

    @Order(3)
    @TestFactory
    Stream<DynamicTest> testUpdateModel() {Iterator<String> inputGenerator = fileList.iterator();
        return fileList.stream().map(element -> DynamicTest.dynamicTest(displayName + element,
                () -> {
                    int id = fileList.indexOf(element);
                    Map<String, String> param = paramList.get(id);
                    JSONObject model = new JSONObject(param.get("modelStringToUpdate"));
                    model.put(param.get("idName"), idMap.get(element));
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
                    HttpEntity<String> entity = new HttpEntity<String>(model.toString(), headers);
                    ResponseEntity<String> response = this.restTemplate.exchange(
                            createURLWithPort(uri + element), HttpMethod.PUT, entity, String.class);
                    assertEquals(expectedResponseCode, response.getStatusCodeValue());
                    String responseString = response.getBody();
                    Assertions.assertTrue(responseString.contains(param.get("responseBodyAfterUpdate")));

                    ResponseEntity<String> newResponse = getModel(element);
                    assertEquals(expectedResponseCode, newResponse.getStatusCodeValue());
                    Assertions.assertTrue(newResponse.getBody().contains(param.get("responseBodyAfterUpdate")));
                }));
    }

    @Order(4)
    @TestFactory
    Stream<DynamicTest> testDeleteModel() {Iterator<String> inputGenerator = fileList.iterator();
        return fileList.stream().map(element -> DynamicTest.dynamicTest(displayName + element,
                () -> {
                    int id = fileList.indexOf(element);
                    Map<String, String> param = paramList.get(id);
                    JSONObject body = new JSONObject();
                    body.put(param.get("idName"), idMap.get(element));
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
                    HttpEntity<String> entity = new HttpEntity<String>(body.toString(), headers);
                    ResponseEntity<String> response = this.restTemplate.exchange(
                            createURLWithPort(uri + element), HttpMethod.DELETE, entity, String.class);
                    assertEquals(expectedResponseCode, response.getStatusCodeValue());

                    ResponseEntity<String> newResponse = getModel(element);
                    assertEquals(expectedResponseCode, newResponse.getStatusCodeValue());
                    Assertions.assertFalse(newResponse.getBody().contains(param.get("responseBodyAfterUpdate")));
                }));
    }
}
