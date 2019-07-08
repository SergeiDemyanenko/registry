package dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {

    private static Connection connection = null;

    synchronized public static Connection getConnection() {
        String url = System.getProperty("dbURL");
        String userName = System.getProperty("dbUserName");
        String password = System.getProperty("dbPassword");
        try {
            if (connection == null) {
                connection = DriverManager.getConnection(url, userName, password);
                System.out.println("Connection has been established");
            }else if (connection.isClosed()) {
                connection = DriverManager.getConnection(url, userName, password);
                System.out.println("Connection has been established");
            }
        } catch (SQLException e) {
            System.out.println("Was not able to set up connection: \n" + e.getMessage());
        }
        return connection;
    }
}
