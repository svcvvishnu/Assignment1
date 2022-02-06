import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class MySQLDBManagerUnitTest {

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
