package registry;

import dataBase.DataBase;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class TestController {

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
        when(rsmd.getColumnName(1)).thenReturn("Name");
        when(rsmd.getColumnName(2)).thenReturn("LastName");
        when(rsmd.getColumnName(3)).thenReturn("Phone");
        when(rs.getString(1)).thenReturn("Phill");
        when(rs.getString(2)).thenReturn("Andersen");
        when(rs.getString(3)).thenReturn("12345");

        assertEquals(dataBase.getJsonFromSQL(mockConnection, "SELECT  * from PERSON"),
                "[{\"Phone\":\"12345\",\"LastName\":\"Andersen\",\"Name\":\"Phill\"},{\"Phone\":\"12345\",\"LastName\":\"Andersen\",\"Name\":\"Phill\"}]");

    }

}
