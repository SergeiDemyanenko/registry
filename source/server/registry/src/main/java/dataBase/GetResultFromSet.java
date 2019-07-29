package dataBase;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface GetResultFromSet<T> {
    T get(ResultSet resultSet) throws SQLException;
}
