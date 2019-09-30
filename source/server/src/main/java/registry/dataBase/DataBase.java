package registry.dataBase;

import org.json.JSONArray;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;

public class DataBase {

    public static JSONArray getJsonFromSQL(DataSource dataSource, String sql) {
        return getJsonFromSQL(dataSource, sql, null);
    }

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
        getResultFromSQL(dataSource, sql, null, getParameterValue, getResultSet);
    }

    public static void getResultFromSQL(DataSource dataSource, String sql, Map<String, String> parameters, GetParameterValue getParameterValue, GetResultSet<SqlRowSet> getResultSet) throws SQLException {
        SqlRowSet sqlRowSet;
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        if (parameters != null) {
            sqlRowSet = jdbcTemplate.queryForRowSet(sql, parameters);
        } else {
            sqlRowSet = jdbcTemplate.queryForRowSet(sql, new GetParameterValueWrapper(getParameterValue));
        }
        getResultSet.get(sqlRowSet);
    }

    public static void getResultFromSQL(DataSource dataSource, String sql, Map<String, String> parameters, GetResultSet<SqlRowSet> getResultSet) throws SQLException {
        getResultFromSQL(dataSource, sql, parameters,null, getResultSet);
    }

    public static JSONArray getJsonFromSQL(DataSource dataSource, String sql, Map<String, String> parameters) {
        try {
            JSONArray jsonData = new JSONArray();

            getResultFromSQL(dataSource, sql, parameters, resultSet -> {
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
