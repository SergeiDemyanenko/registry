package dataBase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBase {

    private static final String URL_PROP_NAME = "dbURL";
    private static final String USER_PROP_NAME = "dbUserName";
    private static final String PASS_PROP_NAME = "dbPassword";

    private static Connection connection = null;

    public static synchronized Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                String url = System.getProperty(URL_PROP_NAME);
                String userName = System.getProperty(USER_PROP_NAME);
                String password = System.getProperty(PASS_PROP_NAME);

                connection = DriverManager.getConnection(url, userName, password);
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
                        if (result.getString(i) != null) {
                            json.put(header.get(i - 1), result.getString(i));
                        }
                    }
                    jsonArray.put(json);
                }
                statement.close();
                result.close();
            }

        } catch (SQLException e) {
            System.out.println("Requet was not completed: \n" + e.getMessage());
        }
        return jsonArray.toString();
    }
}
