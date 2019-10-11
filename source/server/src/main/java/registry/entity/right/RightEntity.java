package registry.entity.right;

import javax.persistence.*;

@Entity
@Table(name = "APP$RIGHT")
public class RightEntity {

    static final String ID_ROLE_FIELD = "ID_ROLE";

    public enum Type {
        REQUEST,
        FORM,
        SET;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RIGHT")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE_", nullable = false)
    private Type type;

    @Column(name = "NAME", nullable = false)
    private String name;

    public Long getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
