package registry.model;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelRepository {
    String GetAllByName (String modelName);
}
