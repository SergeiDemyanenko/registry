package registry.entity.model;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name = "APP$MODEL")
public class ModelEntity {

    static final String ID_MODEL_FIELD = "ID_MODEL";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID_MODEL_FIELD)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @OneToMany
    @JoinColumn(name = ModelItemEntity.ID_MODEL_FIELD)
    @MapKey(name = ModelItemEntity.NAME_MEMBER)
    private Map<String, ModelItemEntity> items;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Map<String, ModelItemEntity> getItems() {
        return items;
    }
}
