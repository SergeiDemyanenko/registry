package registry.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import registry.dataBase.DataBase;
import registry.entity.model.ModelEntity;
import registry.entity.model.ModelItemEntity;

import java.io.IOException;
import java.util.Map;

public class ModelHelper {

    public static byte[] getItem(String modelName, String itemName) throws IOException {
        ModelEntity model = AutowiredForHelper.getModelRepository().findModelByName(modelName);
        return JsonHelper.getAsBytes(getItem(model.getItems(), itemName));
    }

    private static JsonNode getItemSet(Map<String, ModelItemEntity> modelItemMap, Map<String, Object> nameMap) throws IOException {
        ObjectNode result = JsonHelper.createNode();
        for (Map.Entry<String, Object> entry : nameMap.entrySet()) {
            JsonNode node;
            if (entry.getValue() instanceof Map) {
                node = getItemSet(modelItemMap, (Map)entry.getValue());
            } else {
                node = getItem(modelItemMap, entry.getValue().toString());
            }

            if (node == null) {
                result.put(entry.getKey(), entry.getValue().toString());
            } else {
                result.set(entry.getKey(), node);
            }
        }

        return result;
    }

    private static JsonNode getItem(Map<String, ModelItemEntity> modelItemMap, String itemName) throws IOException {
        ModelItemEntity modelItem = modelItemMap.get(itemName);
        if (modelItem != null) {
            switch (modelItem.getType()) {
                case SET:
                    return getItemSet(modelItemMap, JsonHelper.getMapFromString(modelItem.getContent()));
                case FORM:
                case ACTION:
                    return JsonHelper.createValueNode(modelItem.getContent());
                case REQUEST:
                    return JsonHelper.createValueNode(DataBase.getJsonFromSQL(AutowiredForHelper.getDataSource(), modelItem.getContent()).toString());
            }
        }

        return null;
    }
}
