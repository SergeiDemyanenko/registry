package registry.dataBase;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public static String getJsonFromSQL(DataSource dataSource, String sql) {
        try {
            JSONArray result = new JSONArray();

            getResultFromSQL(dataSource, sql, resultSet -> {
                String[] columnNames = resultSet.getMetaData().getColumnNames();
                while (resultSet.next()) {
                    JSONObject json = new JSONObject();
                    for (int i = 1; i <= columnNames.length; i++) {
                        if (resultSet.getString(i) != null) {
                            json.put(columnNames[i - 1], resultSet.getString(i));
                        }
                    }
                    result.put(json);
                }
            });

            return result.toString();
        } catch (SQLException e) {
            System.out.println("Requet was not completed: \n" + e.getMessage());
        }

        return null;
    }
}
