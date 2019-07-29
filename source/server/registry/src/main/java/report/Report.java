package report;

import dataBase.DataBase;

import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Report {

    private static final String GET_REPORT_BODY_SQL =
            " select R.ID_REPORTS_HTML, R.SQL, R.TEXT, R.PARENT_ID" +
            " from APP$REPORTS_HTML R" +
            " where R.REPORTS_ID = %1$d and %2$d = coalesce(R.PARENT_ID, 0)" +
            " order by R.INDEX_";

    private static void appendRow(Connection connection, long id, long root, ResultSet data, StringBuilder resultBuilder) throws SQLException {

        DataBase.getResultFromSQL(connection, String.format(GET_REPORT_BODY_SQL, id, root), reportSet -> {
            while (reportSet.next()) {
                DataBase.getResultFromSQL(connection, reportSet.getString("SQL"),
                        // TODO Fix next line, parameterMetaData.getParameterTypeName(i) - the code should return name of parameter
                        (ParameterMetaData parameterMetaData, int i) -> data.getObject(parameterMetaData.getParameterTypeName(i)),
                        dataSet -> {
                            while (dataSet.next()) {
                                Object[] parameterArray = new Object[dataSet.getMetaData().getColumnCount()];
                                for (int i = 0; i < parameterArray.length; i++) {
                                    parameterArray[i] = dataSet.getObject(i);
                                }

                                resultBuilder.append(String.format(reportSet.getString("TEXT"), parameterArray));
                                appendRow(connection, id, reportSet.getLong("PARENT_ID"), dataSet, resultBuilder);
                            }
                            return null;
                        }
                );
            }
            return null;
        });
    }

    public static String createReport(Connection connection, long id) {
        StringBuilder result = new StringBuilder();
        try {
            appendRow(connection, id, 0L, null, result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
