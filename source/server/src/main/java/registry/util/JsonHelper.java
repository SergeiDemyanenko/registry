package registry.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ValueNode;
import com.fasterxml.jackson.databind.util.RawValue;

import java.io.IOException;
import java.util.Map;

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

    public static Map<String, Object> getMapFromString(String json) throws Exception {
        return mapper.readValue(json, Map.class);
    }

    public static ObjectNode createNode() {
        return mapper.createObjectNode();
    }

    public static ValueNode createValueNode(String str) {
        return mapper.getNodeFactory().rawValueNode(new RawValue(str));
    }
}
