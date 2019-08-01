package controller;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBase {

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

    public static <T> void getResultFromSQL(Connection connection, String sql, GetParameterValue<T> getParameterValue, GetResultSet getResultSet) throws SQLException {
        if (connection != null && !connection.isClosed()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                if (getParameterValue != null) {
                    for (int i = 0; i < statement.getParameterMetaData().getParameterCount(); i++) {
                        statement.setObject(i, getParameterValue.get(statement.getParameterMetaData(), i));
                    }
                }

                try (ResultSet resultSet = statement.executeQuery()) {
                    getResultSet.get(resultSet);
                }
            }
        }
    }

    public static void getResultFromSQL(Connection connection, String sql, GetResultSet getResultSet) throws SQLException {
        getResultFromSQL(connection, sql, null, getResultSet);
    }

    public static String getJsonFromSQL(Connection connection, String sql) {
        try {
            JSONArray jsonArray = new JSONArray();

            getResultFromSQL(connection, sql, resultSet -> {
                List<JSONObject> jsonList = new ArrayList<>();
                ResultSetMetaData rsmd;

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
