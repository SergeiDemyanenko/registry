package registry.entity.report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportItemRepository extends JpaRepository<ReportItemEntity, Long> {

    List<ReportItemEntity> findAllByReportId(Long reportId);
}
