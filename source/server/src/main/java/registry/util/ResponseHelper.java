package registry.util;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.validation.constraints.Null;
import java.nio.charset.StandardCharsets;

public class ResponseHelper {

    public static ResponseEntity<Resource> get(byte[] body, MediaType mediaType, @Null String fileName) {

        ByteArrayResource resourceBody = new ByteArrayResource(body);
        ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.ok();
        if (fileName != null) {
            bodyBuilder.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        }

        return bodyBuilder
                .contentType(mediaType)
                .contentLength(resourceBody.contentLength())
                .body(resourceBody);
    }

    public static ResponseEntity<Resource> get(byte[] body, MediaType mediaType) {
        return get(body, mediaType, null);
    }

    public static ResponseEntity<Resource> getAsJson(byte[] body) {
        return get(body, MediaType.APPLICATION_JSON_UTF8, null);
    }

    public static ResponseEntity<Resource> get(String body, MediaType mediaType, @Null String fileName) {
        return get(body.getBytes(StandardCharsets.UTF_8), mediaType, fileName);
    }

    public static ResponseEntity<Resource> get(String body, MediaType mediaType) {
        return get(body, mediaType, null);
    }
}
