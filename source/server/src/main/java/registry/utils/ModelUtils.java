package registry.utils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import registry.dataBase.DataBase;
import registry.entity.model.ModelItemEntity;
import registry.entity.model.ModelItemRepository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ModelUtils {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private ModelItemRepository modelItemRepository;

    public String GetAllByName(String modelName) {
        ModelItemEntity model = modelItemRepository.findModelByName(modelName);
        JSONArray jsonHead = new JSONArray();
        String sql = model.getSqlSelect();
        String jsonHeadString = model.getHeaderView();
        List list = new ArrayList<String>(Arrays.asList(jsonHeadString.split(",")));
        list.forEach(item -> jsonHead.put(item.toString().replaceAll("\"", "")));
        JSONArray jsonData = DataBase.getJsonFromSQL(dataSource, sql);
        return new JSONObject().put("HEAD", jsonHead).put("DATA", jsonData).toString();
    }
}
