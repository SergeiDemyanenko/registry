package dataBase;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface GetResultSet {
    void get(ResultSet resultSet) throws SQLException;
}
