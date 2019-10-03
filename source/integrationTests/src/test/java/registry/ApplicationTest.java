package registry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.Assert.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
                classes = Application.class)
public class ApplicationTest {

    @LocalServerPort
    private int port;

    private static JSONObject model;
    private TestRestTemplate restTemplate = new TestRestTemplate();
    private static Integer id;
    private String expectedResponseBodyAfterUpdate = "John\",\"Jason\",\"Donata\",\"1234567890\",\"2019-09-08\",\"ACDF\"," +
            "\"2019-09-08\",\"ESD\",\"12345\",\"Street\",\"2019-09-08\",\"City\"";


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
        return "http://localhost:" + this.port + uri;
    }

    private ResponseEntity<String> getModel() {
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/model/person/get"), HttpMethod.GET, entity, String.class);
        return response;
    }

    @BeforeAll
    public static void setEnvironment() {
        model = new JSONObject();
        model.put("NAME", "Sam");
        model.put("SNAME", "Jason");
        model.put("PNAME", "Donata");
        model.put("PHONE", "1234567890");
        model.put("PASS_ISSUE", "2019-09-08");
        model.put("PASS_ISSUE_CODE", "ACDF");
        model.put("PASS_DATE", "2019-09-08");
        model.put("PASS_SERIES", "ESD");
        model.put("PASS_NUMBER", "12345");
        model.put("PASS_ARRDESS", "Street");
        model.put("BIRTH_DATE", "2019-09-08");
        model.put("BIRTH_PLACE", "City");
        id = 0;
    }

    @AfterAll
    public void cleanDbEntry() {
        JSONObject body = new JSONObject();
        body.put("ID_PERSON", id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<String>(body.toString(), headers);
        ResponseEntity<String> response = this.restTemplate.exchange(
                createURLWithPort("/api/model/person/sql_delete"), HttpMethod.DELETE, entity, String.class);
    }

    @Test
    @Order(1)
    public void testGetModel() {
        String expectedResponseBody = "\"Валерий\",\"Ильин\",\"Геральдович\",\"\",\"\",\"\",\"2019-09-08\",\"\",\"\"," +
                "\"\",\"1955-06-12\",\"\"],[6,\"Василий\",\"Касмынин\",\"Павлович\",\"\",\"\",\"\",\"2019-09-08\"";
        ResponseEntity<String> response = getModel();
        String expected = "\"Валерий\",\"Ильин\",\"Геральдович\",\"\",\"\",\"\",\"2019-09-08\",\"\",\"\",\"\",\"1955-06-12\",\"\"],[6,\"Василий\",\"Касмынин\",\"Павлович\",\"\",\"\",\"\",\"2019-09-08\"";
        assertEquals(response.getStatusCodeValue(), 200);
        Assertions.assertTrue(response.getBody().contains(expectedResponseBody));
        Assertions.assertTrue(isJSONValid(response.getBody()));
    }

    @Test
    @Order(2)
    public void testInsertModel() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String expectedResponseBody = "Sam\",\"Jason\",\"Donata\",\"1234567890\",\"2019-09-08\",\"ACDF\"," +
                                        "\"2019-09-08\",\"ESD\",\"12345\",\"Street\",\"2019-09-08\",\"City\"";
        HttpEntity<String> entity = new HttpEntity<String>(model.toString(), headers);
        ResponseEntity<String> response = this.restTemplate.exchange(
                createURLWithPort("/api/model/person/sql_insert"), HttpMethod.POST, entity, String.class);
        assertEquals(response.getStatusCodeValue(), 200);
        String responseString = response.getBody();
        Assertions.assertTrue(responseString.contains(expectedResponseBody));

        ResponseEntity<String> newResponse = getModel();
        assertEquals(newResponse.getStatusCodeValue(), 200);
        Assertions.assertTrue(newResponse.getBody().contains(expectedResponseBody));

        id = Integer.valueOf(responseString.split(",")[0].substring(2));
        System.out.println(id);
    }

    @Test
    @Order(3)
    public void testUpdateModel() {
        System.out.println(id);
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
        Assertions.assertTrue(responseString.contains(expectedResponseBodyAfterUpdate));

        ResponseEntity<String> newResponse = getModel();
        assertEquals(200, newResponse.getStatusCodeValue());
        Assertions.assertTrue(newResponse.getBody().contains(expectedResponseBodyAfterUpdate));
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
        Assertions.assertEquals(response.getBody(), body.toString());

        ResponseEntity<String> newResponse = getModel();
        assertEquals(200, newResponse.getStatusCodeValue());
        Assertions.assertFalse(newResponse.getBody().contains(expectedResponseBodyAfterUpdate));
    }


}
