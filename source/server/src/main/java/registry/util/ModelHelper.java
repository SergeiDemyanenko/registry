package registry.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import registry.dataBase.DataBase;
import registry.entity.model.ModelEntity;
import registry.entity.model.ModelItemEntity;
import registry.exceptions.RequestNotFoundException;
import registry.exceptions.ModelNotFoundException;

import java.util.Map;

public class ModelHelper {

    private static final String CONST_PREFIX = "const.";

    public static byte[] getItem(String modelName, String itemName, Map<String, String> parameters) throws Exception {
        ModelEntity model = AutowiredForHelper.getModelRepository().findModelByName(modelName);
        if (model == null) throw new ModelNotFoundException(modelName);

        return JsonHelper.getAsBytes(getItem(model.getItems(), itemName, parameters));
    }

    private static JsonNode getItemSet(Map<String, ModelItemEntity> modelItemMap, Map<String, Object> nameMap, Map<String, String> parameters) throws Exception {
        ObjectNode result = JsonHelper.createNode();
        for (Map.Entry<String, Object> entry : nameMap.entrySet()) {
            JsonNode node = null;
            String itemName = null;
            if (entry.getValue() instanceof Map) {
                node = getItemSet(modelItemMap, (Map)entry.getValue(), parameters);
            } else {
                itemName = entry.getValue().toString();
                if (itemName.startsWith(CONST_PREFIX)) {
                    itemName = itemName.substring(CONST_PREFIX.length());
                } else {
                    node = getItem(modelItemMap, itemName, parameters);
                }
            }

            if (node == null) {
                result.put(entry.getKey(), itemName);
            } else {
                result.set(entry.getKey(), node);
            }
        }

        return result;
    }

    private static JsonNode getItem(Map<String, ModelItemEntity> modelItemMap, String itemName, Map<String, String> parameters) throws Exception {
        ModelItemEntity modelItem = modelItemMap.get(itemName);
        if (modelItem == null) throw new RequestNotFoundException(itemName);

        switch (modelItem.getType()) {
            case SET:
                return getItemSet(modelItemMap, JsonHelper.getMapFromString(modelItem.getContent()), parameters);
            case REQUEST:
                return JsonHelper.createValueNode(DataBase.getJsonFromSQL(AutowiredForHelper.getDataSource(), modelItem.getContent(), parameters).toString());
            default:
                return JsonHelper.createValueNode(modelItem.getContent());
        }
    }
}
