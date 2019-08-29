package registry.menu;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "APP$MENU")
public class MenuItemEntity implements Comparable<MenuItemEntity> {

    @Id
    @GeneratedValue
    @Column(name = "ID_MENU")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "URL")
    private String url;

    @ManyToOne
    @Column(name = "ID_PARENT")
    private Long idParent;

    @Column(name = "NUMBER")
    private Long number;

    @OneToMany
    @JoinColumn(name = "ID_PARENT")
    private List<MenuItemEntity> children;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public Long getIdParent() {
        return idParent;
    }

    public Long getNumber() {
        return number;
    }

    public List<MenuItemEntity> getChildren() {
        return children;
    }

    @Override
    public int compareTo(MenuItemEntity o) {
        return Long.compare(this.getNumber(), o.getNumber());
    }
}
