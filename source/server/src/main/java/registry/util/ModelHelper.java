package registry.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.RawValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import registry.dataBase.DataBase;
import registry.entity.model.ModelEntity;
import registry.entity.model.ModelItemEntity;
import registry.entity.model.ModelRepository;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Map;

@Component
public class ModelHelper {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private ModelRepository modelRepository;

    public String getModelItem(String modelName, String modelItemName) throws IOException {
        ModelEntity model = modelRepository.findModelByName(modelName);
        JsonNode result = getModelItem(model.getItems(), modelItemName);
        if (result != null) {
            return JsonHelper.getAsString(result);
        }

        return null;
    }

    private JsonNode getModelItem(Map<String, ModelItemEntity> modelItemMap, String modelItemName) throws IOException {
        ModelItemEntity modelItem = modelItemMap.get(modelItemName);
        if (modelItem != null) {
            switch (modelItem.getType()) {
                case SET:
                    ObjectNode result = JsonHelper.createNode();
                    for (Map.Entry<String, String> entry : JsonHelper.getMapFromString(modelItem.getContent()).entrySet()) {
                        JsonNode node = getModelItem(modelItemMap, entry.getValue());
                        if (node == null) {
                            result.put(entry.getKey(), entry.getValue());
                        } else {
                            result.put(entry.getKey(), node);
                        }
                    }
                    return result;
                case FORM:
                case ACTION:
                    return JsonHelper.createNode().rawValueNode(new RawValue(modelItem.getContent()));
                case REQUEST:
                    return JsonHelper.createNode().rawValueNode(new RawValue(DataBase.getJsonFromSQL(dataSource, modelItem.getContent()).toString()));
            }
        }

        return null;
    }
}
