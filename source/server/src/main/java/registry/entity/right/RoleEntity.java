package registry.entity.right;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "APP$ROLE")
public class RoleEntity {

    static final String ID_ROLE_FIELD = "ID_ROLE";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID_ROLE_FIELD)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @OneToMany
    @JoinColumn(name = RightEntity.ID_ROLE_FIELD)
    private Set<RightEntity> rights;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<RightEntity> getRights() {
        return rights;
    }
}
