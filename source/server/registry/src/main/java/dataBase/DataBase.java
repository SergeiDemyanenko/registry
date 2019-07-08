package dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
}
