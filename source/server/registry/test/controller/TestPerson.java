package controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class TestPerson {

    @InjectMocks
    private DataBase dataBase = mock(DataBase.class);
    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockStatement;
    @Mock
    private ResultSet rs;
    @Mock
    private ResultSetMetaData rsmd;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testPerson() throws SQLException {
        when(mockConnection.prepareStatement("SELECT  * from PERSON")).thenReturn(mockStatement);
        when(mockStatement.getResultSet()).thenReturn(rs);
        when(mockStatement.executeQuery()).thenReturn(rs);
        when(rs.getMetaData()).thenReturn(rsmd);
        when(rsmd.getColumnCount()).thenReturn(3);
        when(rs.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(rsmd.getCatalogName(1)).thenReturn("Person1");
        when(rsmd.getCatalogName(2)).thenReturn("Person2");
        when(rsmd.getCatalogName(3)).thenReturn("Person3");

        assertEquals(dataBase.getJsonFromSQL(mockConnection, "SELECT  * from PERSON"), "[{},{}]");

    }

}
