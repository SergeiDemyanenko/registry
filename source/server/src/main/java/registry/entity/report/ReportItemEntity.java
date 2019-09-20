package registry.entity.report;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "APP$REPORTS_HTML")
public class ReportItemEntity implements Comparable<ReportItemEntity> {

    @Id
    @GeneratedValue
    @Column(name="ID_REPORTS_HTML")
    private Long id;

    @Column(name="SQL")
    private String sql;

    @Column(name="TEXT")
    private String text;

    @Column(name="PARENT_ID")
    private Long rootId;

    @Column(name="REPORTS_ID")
    private Long reportId;

    @Column(name="INDEX_")
    private Long index;

    public Long getId() {
        return id;
    }

    public String getSql() {
        return sql;
    }

    public String getText() {
        return text;
    }

    public Long getRootId() {
        return rootId;
    }

    public Long getReportId() {
        return reportId;
    }

    public Long getIndex() {
        return index;
    }

    public static ReportItemEntity from(Long reportId) {
        ReportItemEntity result = new ReportItemEntity();
        result.reportId = reportId;
        return result;
    }

    @Override
    public int compareTo(ReportItemEntity o) {
        return Long.compare(this.getIndex(), o.getIndex());
    }
}
