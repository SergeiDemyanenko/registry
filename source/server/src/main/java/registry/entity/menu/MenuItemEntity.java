package registry.entity.menu;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "APP$MENU")
public class MenuItemEntity implements Comparable<MenuItemEntity> {

    @Id
    @GeneratedValue
    @Column(name = "ID_MENU")
    @JsonIgnore
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "URL")
    private String url;

    @Column(name = "PARENT_ID")
    @JsonIgnore
    private Long parentId;

    @Column(name = "INDEX_")
    private Long index;

    @OneToMany
    @JoinColumn(name = "PARENT_ID")
    @JsonProperty("items")
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

    public Long getParentId() {
        return parentId;
    }

    public Long getIndex() {
        return index;
    }

    public List<MenuItemEntity> getChildren() {
        return children;
    }

    @Override
    public int compareTo(MenuItemEntity o) {
        return Long.compare(this.getIndex(), o.getIndex());
    }
}
