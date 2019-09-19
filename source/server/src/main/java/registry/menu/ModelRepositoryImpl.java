package registry.menu;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import registry.dataBase.DataBase;
import registry.model.ModelItemEntity;
import registry.model.ModelRepository;

import javax.sql.DataSource;

@Repository
public class ModelRepositoryImpl implements ModelRepository {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public String GetAllByName(String modelName) {
        Session session = sessionFactory.getCurrentSession();
        ModelItemEntity model = session.get(ModelItemEntity.class, modelName);
        String sql = model.getSqlSelect();
        return DataBase.getJsonFromSQL(dataSource, sql);
    }
}
