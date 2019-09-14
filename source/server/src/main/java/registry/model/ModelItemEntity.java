package registry.model;

import javax.persistence.*;

@Entity
@Table(name="APP$MODEL")
public class ModelItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MODEL")
    private int id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "HEADER_EDIT")
    private String headerEdit;

    @Column(name = "SQL_INSERT")
    private String sqlInsert;

    @Column(name = "SQL_UPDATE")
    private String sqlUpdate;

    @Column(name = "HEADER_VIEW")
    private String headerView;

    @Column(name = "SQL_SELECT")
    private String sqlSelect;

    @Column(name = "SQL_DELETE")
    private String sqlDelete;


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getHeaderEdit() {
        return headerEdit;
    }

    public String getSqlInsert() {
        return sqlInsert;
    }

    public String getSqlUpdate() {
        return sqlUpdate;
    }

    public String getHeaderView() {
        return headerView;
    }

    public String getSqlSelect() {
        return sqlSelect;
    }

    public String getSqlDelete() {
        return sqlDelete;
    }
}
