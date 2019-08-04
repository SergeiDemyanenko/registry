package dataBase;

import java.sql.ParameterMetaData;
import java.sql.SQLException;

@FunctionalInterface
public interface GetParameterValue<T> {
    T get(ParameterMetaData parameterMetaData, int index) throws SQLException;
}
