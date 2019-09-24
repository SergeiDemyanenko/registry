package registry.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonHelper {

    private static final ObjectMapper mapper;
    static {
        mapper = new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    }

    public static String getAsString(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }

    public static byte[] getAsBytes(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsBytes(obj);
    }

}
