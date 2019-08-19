package registry.report;

import com.google.common.base.Strings;
import org.springframework.data.domain.Example;
import registry.dataBase.DataBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class Report {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private ReportItemRepository reportItemRepository;

    private Object[] getArrayFromRow(SqlRowSet resultSet) {
        Object[] result = new Object[resultSet.getMetaData().getColumnCount()];
        for (int i = 1; i <= result.length; i++) {
            result[i - 1] = resultSet.getObject(i);
        }
        return result;
    }

    private void appendRow(List<ReportItemEntity> reportItemEntityList, Long rootId, SqlRowSet data, StringBuilder resultBuilder) {

        reportItemEntityList.stream().filter(reportItemEntity -> Objects.equals(reportItemEntity.getRootId(), rootId)).sorted().forEach(item -> {
                if (!Strings.isNullOrEmpty(item.getSql())) {
                    try {
                        DataBase.getResultFromSQL(dataSource, item.getSql(),
                                name -> data == null ? 3 : data.getObject(name),
                                itemData -> {
                                    while (itemData.next()) {
                                        if (!Strings.isNullOrEmpty(item.getText())) {
                                            resultBuilder.append(String.format(item.getText().replace(":s", "$s"), getArrayFromRow(itemData)));
                                        }
                                        appendRow(reportItemEntityList, item.getId(), itemData, resultBuilder);
                                    }
                                }
                        );
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else if (!Strings.isNullOrEmpty(item.getText())) {
                    resultBuilder.append(item.getText());
                }
        });
    }

    public String createReport(Long id) {

        StringBuilder result = new StringBuilder();
        appendRow(this.reportItemRepository.findAllByReportId(id), null, null, result);
        return result.toString();
    }
}
