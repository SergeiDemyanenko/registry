package registry.menu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItemEntity, Long> {

    List<MenuItemEntity> findAllByParentId(Long parentId);

    default List<MenuItemEntity> findAllRoot() {
        return findAllByParentId(null);
    }

}
