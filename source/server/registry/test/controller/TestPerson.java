package controller;

import org.junit.Test;

import java.sql.Connection;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestPerson {

    private DataBase dataBase = mock(DataBase.class);

    @Test
    public void testPerson() {
        Connection connection = dataBase.getConnection();
        when(dataBase.getJsonFromSQL(connection, "SELECT  * from PERSON")).thenReturn("");

    }

}
