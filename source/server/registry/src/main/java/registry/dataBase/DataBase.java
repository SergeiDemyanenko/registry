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

    private static final String URL_PROP_NAME = "database";

    private static Connection connection = null;

    public static synchronized Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(System.getProperty(URL_PROP_NAME), System.getProperties());
                System.out.println("Connection has been established");
            }
        } catch (SQLException e) {
            System.out.println("Was not able to set up connection: \n" + e.getMessage());
        }
        return connection;
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
            JSONArray jsonArray = new JSONArray();

            getResultFromSQL(dataSource, sql, resultSet -> {
                List<JSONObject> jsonList = new ArrayList<>();
                SqlRowSetMetaData rsmd;

                rsmd = resultSet.getMetaData();
                int columnNumber = rsmd.getColumnCount();
                if (columnNumber == 0) {
                    throw new SQLException("Table does not exist");
                }
                JSONObject json;
                ArrayList<String> header = new ArrayList<String>();
                for (int i = 1; i <= columnNumber; i++) {
                    header.add(rsmd.getColumnName(i));
                }
                while (resultSet.next()) {
                    json = new JSONObject();
                    for (int i = 1; i <= columnNumber; i++) {
                        if (resultSet.getString(i) != null && !header.get(i-1).startsWith("ID")) {
                            json.put(header.get(i - 1), resultSet.getString(i));
                        }
                    }
                    jsonList.add(json);
                }

                for(JSONObject item : jsonList) {
                    jsonArray.put(item);
                }
            });

            return jsonArray.toString();
        } catch (SQLException e) {
            System.out.println("Requet was not completed: \n" + e.getMessage());
        }

        return null;
    }
}
