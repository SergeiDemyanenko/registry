package registry.utils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import registry.dataBase.DataBase;
import registry.entity.model.ModelItemEntity;
import registry.entity.model.ModelItemRepository;

import javax.sql.DataSource;

@Component
public class ModelUtils {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private ModelItemRepository modelItemRepository;

    public String executeModel(String modelName) {
        ModelItemEntity modelItem = modelItemRepository.findModelByName(modelName);

        JSONArray jsonHead = new JSONArray(modelItem.getHeaderView());
        JSONArray jsonData = DataBase.getJsonFromSQL(dataSource, modelItem.getSqlSelect());

        return new JSONObject().put("HEAD", jsonHead).put("DATA", jsonData).toString();
    }
}
