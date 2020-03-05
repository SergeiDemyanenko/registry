package registry.dataBase;

import org.json.JSONArray;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;

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

        @Override
        public int getSqlType(String paramName) {
            return 0;
        }

        @Override
        public String getTypeName(String paramName) {
            return null;
        }
    }

    public static void getResultFromSQL(DataSource dataSource, String sql, GetParameterValue getParameterValue, GetResultSet<SqlRowSet> getResultSet) throws SQLException {
        SqlParameterSource sqlParameterSource = new GetParameterValueWrapper(getParameterValue);
        getResultFromSQL(dataSource, sql, sqlParameterSource, getResultSet);
    }

    private static void getResultFromSQL(DataSource dataSource, String sql, SqlParameterSource sqlParameterSource, GetResultSet<SqlRowSet> getResultSet) throws SQLException {
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql, sqlParameterSource);
        getResultSet.get(sqlRowSet);
    }

    public static JSONArray getJsonFromSQL(DataSource dataSource, String sql) {
        return getJsonFromSQL(dataSource, sql, (Map)null);
    }

    public static JSONArray getJsonFromSQL(DataSource dataSource, String sql, Map<String, String> parameters) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(parameters);
        return getJsonFromSQL(dataSource, sql, sqlParameterSource);
    }

    private static JSONArray getJsonFromSQL(DataSource dataSource, String sql, SqlParameterSource sqlParameterSource) {
        try {
            JSONArray jsonData = new JSONArray();

            getResultFromSQL(dataSource, sql, sqlParameterSource, resultSet -> {
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
