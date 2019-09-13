package registry.report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.util.List;

@Repository
public interface ReportItemRepository extends JpaRepository<ReportItemEntity, Long> {

    List<ReportItemEntity> findAllByReportId(Long reportId);
}
