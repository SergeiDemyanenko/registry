package report;

import dataBase.DataBase;

import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Report {

    private static final String GET_REPORT_BODY_SQL =
            " select R.ID_REPORTS_HTML as ID, R.SQL, R.TEXT, R.PARENT_ID" +
            " from APP$REPORTS_HTML R" +
            " where R.REPORTS_ID = %1$d and %2$d = coalesce(R.PARENT_ID, 0)" +
            " order by R.INDEX_";

    private static Object[] getArrayFromRow(ResultSet resultSet) throws SQLException {
        Object[] result = new Object[resultSet.getMetaData().getColumnCount()];
        for (int i = 1; i <= result.length; i++) {
            result[i - 1] = resultSet.getObject(i);
        }
        return result;
    }

    private static void appendRow(Connection connection, long id, long root, ResultSet data, StringBuilder resultBuilder) throws SQLException {

        DataBase.getResultFromSQL(connection, String.format(GET_REPORT_BODY_SQL, id, root), reportSet -> {
            while (reportSet.next()) {
                String itemSql = reportSet.getString("SQL");
                String itemText = reportSet.getString("TEXT");
                Long itemRoot = reportSet.getLong("ID");

                if (itemSql != null && !itemSql.isEmpty()) {
                    DataBase.getResultFromSQL(connection, itemSql,
                            // TODO Fix next line, parameterMetaData.getParameterTypeName(i) - the code should return name of parameter
                            (ParameterMetaData parameterMetaData, int i) -> data.getObject(parameterMetaData.getParameterTypeName(i)),
                            itemData -> {
                                while (itemData.next()) {
                                    if (itemText != null && !itemText.isEmpty()) {
                                        resultBuilder.append(String.format(itemText.replace(":s", "$s"), getArrayFromRow(itemData)));
                                    }
                                    appendRow(connection, id, itemRoot, itemData, resultBuilder);
                                }
                            }
                    );
                } else {
                    String text = reportSet.getString("TEXT");
                    if (text != null && !text.isEmpty()) {
                        resultBuilder.append(text);
                    }
                }
            }
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
