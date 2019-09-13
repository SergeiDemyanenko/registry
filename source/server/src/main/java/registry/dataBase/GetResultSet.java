package registry.dataBase;

import java.sql.SQLException;

@FunctionalInterface
public interface GetResultSet<T> {
    void get(T rowSet) throws SQLException;
}
