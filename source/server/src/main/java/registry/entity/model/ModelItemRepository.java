package registry.entity.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelItemRepository extends CrudRepository<ModelItemEntity,String> {
    ModelItemEntity findModelByName(String name);
}
