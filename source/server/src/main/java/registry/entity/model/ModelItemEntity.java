package registry.entity.model;

import javax.persistence.*;

@Entity
@Table(name = "APP$MODEL_ITEM")
public class ModelItemEntity {

    static final String NAME_MEMBER = "name";
    static final String ID_MODEL_FIELD = "ID_MODEL";

    public enum Type {
        REQUEST,
        FORM,
        ACTION,
        SET;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MODEL_ITEM")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE_")
    private Type type;

    @Column(name = "CONTENT")
    private String content;

    @ManyToOne
    @JoinColumn(name = ModelEntity.ID_MODEL_FIELD, nullable = false)
    private ModelEntity model;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public ModelEntity getModel() {
        return model;
    }
}
