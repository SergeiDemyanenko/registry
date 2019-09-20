package registry.dataBase;

import org.json.JSONArray;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.sql.SQLException;

public class DataBase {

    private static class GetParameterValueWrapper implements SqlParameterSource {

        private GetParameterValue getParameterValue;

        public GetParameterValueWrapper(GetParameterValue getParameterValue) {
            this.getParameterValue = getParameterValue;
        }

        @Override
        public boolean hasValue(String paramName) {
            return this.getParameterValue != null;
        }

        @Override
        public Object getValue(String paramName) throws IllegalArgumentException {
            return getParameterValue.get(paramName);
        }
    }

    public static void getResultFromSQL(DataSource dataSource, String sql, GetParameterValue getParameterValue, GetResultSet<SqlRowSet> getResultSet) throws SQLException {

        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql, new GetParameterValueWrapper(getParameterValue));
        getResultSet.get(sqlRowSet);
    }

    public static void getResultFromSQL(DataSource dataSource, String sql, GetResultSet<SqlRowSet> getResultSet) throws SQLException {
        getResultFromSQL(dataSource, sql, null, getResultSet);
    }

    public static JSONArray getJsonFromSQL(DataSource dataSource, String sql) {
        try {
            JSONArray jsonData = new JSONArray();

            getResultFromSQL(dataSource, sql, resultSet -> {
                String[] columnNames = resultSet.getMetaData().getColumnNames();
                while (resultSet.next()) {
                    JSONArray jsonRow = new JSONArray();
                    for (int i = 1; i <= columnNames.length; i++) {
                        jsonRow.put(resultSet.getObject(i));
                    }
                    jsonData.put(jsonRow);
                }
            });

            return jsonData;
        } catch (SQLException e) {
            System.out.println("Requet was not completed: \n" + e.getMessage());
        }

        return null;
    }
}
