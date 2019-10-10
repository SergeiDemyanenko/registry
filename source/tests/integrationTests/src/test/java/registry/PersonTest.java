package registry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class PersonTest extends ApplicationTest{

    private static JSONObject model;
    private TestRestTemplate restTemplate = new TestRestTemplate();
    private static Integer id;
    private static List<String> fileList;
    private static List<Map> param;

    @Value("${responseBodyAfterUpdate}")
    private String responseBodyAfterUpdate;
    @Value("${expectedResponseBody}")
    private String expectedResponseBody;
    @Value("${responseBodyBeforeUpdate}")
    private String responseBodyBeforeUpdate;
    @Value("${modelString}")
    private String modelString;

    @BeforeAll
    public void setUpEnvironment() {

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

    private ResponseEntity<String> getModel() {
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/model/person/get"), HttpMethod.GET, entity, String.class);
        return response;
    }

    @Test
    @Order(1)
    public void testGetModel() {
        ResponseEntity<String> response = getModel();
        assertEquals(response.getStatusCodeValue(), 200);
        Assertions.assertTrue(isJSONValid(response.getBody()));
    }

    @Test
    @Order(2)
    public void testInsertModel() {
        System.out.println(modelString);
        model = new JSONObject(modelString);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<String>(model.toString(), headers);
        ResponseEntity<String> response = this.restTemplate.exchange(
                createURLWithPort("/api/model/person/sql_insert"), HttpMethod.POST, entity, String.class);
        assertEquals(response.getStatusCodeValue(), 200);
        String responseString = response.getBody();
        Assertions.assertTrue(responseString.contains(responseBodyBeforeUpdate));

        ResponseEntity<String> newResponse = getModel();
        assertEquals(newResponse.getStatusCodeValue(), 200);
        Assertions.assertTrue(newResponse.getBody().contains(responseBodyBeforeUpdate));
        id = Integer.valueOf(responseString.split(",")[0].substring(2));
        System.out.println(id);
    }

    @Test
    @Order(3)
    public void testUpdateModel() {
        model = new JSONObject(modelString);
        model.remove("NAME");
        model.put("NAME", "John");
        model.put("ID_PERSON", id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<String>(model.toString(), headers);
        ResponseEntity<String> response = this.restTemplate.exchange(
                createURLWithPort("/api/model/person/sql_update"), HttpMethod.PUT, entity, String.class);
        assertEquals(200, response.getStatusCodeValue());
        String responseString = response.getBody();
        Assertions.assertTrue(responseString.contains(responseBodyAfterUpdate));

        ResponseEntity<String> newResponse = getModel();
        assertEquals(200, newResponse.getStatusCodeValue());
        System.out.println("response body: " + responseBodyAfterUpdate);
        Assertions.assertTrue(newResponse.getBody().contains(responseBodyAfterUpdate));
    }

    @Test
    @Order(4)
    public void deleteModel() {
        JSONObject body = new JSONObject();
        body.put("ID_PERSON", id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<String>(body.toString(), headers);
        ResponseEntity<String> response = this.restTemplate.exchange(
                createURLWithPort("/api/model/person/sql_delete"), HttpMethod.DELETE, entity, String.class);
        assertEquals(200, response.getStatusCodeValue());

        ResponseEntity<String> newResponse = getModel();
        assertEquals(200, newResponse.getStatusCodeValue());
        Assertions.assertFalse(newResponse.getBody().contains(responseBodyAfterUpdate));
    }


}