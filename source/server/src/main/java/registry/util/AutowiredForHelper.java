package registry.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import registry.entity.model.ModelRepository;
import javax.sql.DataSource;

@Component
public class AutowiredForHelper {

    private static DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource been) {
        dataSource = been;
    }

    public static DataSource getDataSource() {
        return dataSource;
    }

    private static ModelRepository modelRepository;

    @Autowired
    public void setModelRepository(ModelRepository been) {
        modelRepository = been;
    }

    public static ModelRepository getModelRepository() {
        return modelRepository;
    }
}
