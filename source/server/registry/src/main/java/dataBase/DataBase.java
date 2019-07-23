package dataBase;

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

    public static String executeQuery(Connection connection, String sqlQuery) {
        ResultSet result;
        JSONArray jsonArray = new JSONArray();
        List<JSONObject> jsonList = new ArrayList<>();
        ResultSetMetaData rsmd;
        try {
            if (connection != null && !connection.isClosed()) {
                Statement statement = connection.createStatement();
                result = statement.executeQuery(sqlQuery);
                rsmd = result.getMetaData();
                int columnNumber = rsmd.getColumnCount();
                if (columnNumber == 0) {
                    throw new SQLException("Table does not exist");
                }
                JSONObject json;
                ArrayList<String> header = new ArrayList<String>();
                for (int i = 1; i <= columnNumber; i++) {
                    header.add(rsmd.getColumnName(i));
                }
                while (result.next()) {
                    json = new JSONObject();
                    for (int i = 1; i <= columnNumber; i++) {
                        if (result.getString(i) != null && !header.get(i-1).startsWith("ID")) {
                            json.put(header.get(i - 1), result.getString(i));
                        }
                    }
                    jsonList.add(json);
                }
                statement.close();
                result.close();
            }

        } catch (SQLException e) {
            System.out.println("Requet was not completed: \n" + e.getMessage());
        }

        for(JSONObject json: jsonList) {
            jsonArray.put(json);
        }

        return jsonArray.toString();
    }
}
