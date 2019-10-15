package registry.entity.right;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "APP$USER")
public class UserEntity {

    static final String ID_USER_FIELD = "ID_USER";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID_USER_FIELD)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "PASSWORD")
    private String password;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "APP$USER_ROLE",
            joinColumns = @JoinColumn(name = ID_USER_FIELD),
            inverseJoinColumns = @JoinColumn(name = RoleEntity.ID_ROLE_FIELD))
    private Set<RightEntity> roles;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
