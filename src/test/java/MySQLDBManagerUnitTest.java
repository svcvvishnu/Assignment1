import Exceptions.DatabaseNotFoundException;
import Exceptions.InvalidDatabaseNameException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;


public class MySQLDBManagerUnitTest {
    QueryCommand cmd;

    @Mock
    MySQLDBManager dbMgr;

    @Mock
    Connection mockConnection;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test() throws SQLException {
        when(dbMgr.getConnection()).thenReturn(mockConnection);
        DatabaseMetaData mockrsm = mock(DatabaseMetaData.class);
        when(mockConnection.getMetaData()).thenReturn(mockrsm);
        ResultSet mockrs = mock(ResultSet.class);
        when(mockrsm.getCatalogs()).thenReturn(mockrs);
        when(mockrs.next()).thenReturn(true,true,false);
        when(mockrs.getString(0)).thenReturn("ABC");
        when(mockrs.getString(1)).thenReturn(Constants.DATABASE);
        when(dbMgr.isDatabaseAvailable()).thenCallRealMethod();
        Assert.assertTrue(dbMgr.isDatabaseAvailable());
    }
}
