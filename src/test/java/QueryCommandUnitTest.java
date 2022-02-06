import Exceptions.DatabaseNotFoundException;
import Exceptions.InvalidDatabaseNameException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;


public class QueryCommandUnitTest {
    QueryCommand cmd;

    @Mock
    MySQLDBManager dbMgr;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test() throws SQLException {
        when(dbMgr.isDatabaseAvailable()).thenReturn(true);
        cmd = new QueryCommand(dbMgr, Constants.DATABASE, "Select * from XYZ");
        List<String[]> res = new ArrayList<>();
        res.add(new String[] {"Col A", "col B"});
        res.add(new String[] {"A", "B"});
        when(dbMgr.executeQuery(anyString())).thenReturn(res);
        cmd.run();
        Assert.assertTrue(new File("results.csv").exists());
    }

    @Test(expected = InvalidDatabaseNameException.class)
    public void testInvalidDB() {
        cmd = new QueryCommand(dbMgr, "XYZ", "Select * from XYZ");
        cmd.run();
    }

    @Test(expected = DatabaseNotFoundException.class)
    public void testNoDB() throws SQLException {
        when(dbMgr.isDatabaseAvailable()).thenReturn(false);
        cmd = new QueryCommand(dbMgr, Constants.DATABASE, "Select * from XYZ");
        cmd.run();
    }

    @Test(expected = RuntimeException.class)
    public void testExecuteFails() throws SQLException {
        when(dbMgr.isDatabaseAvailable()).thenReturn(false);
        cmd = new QueryCommand(dbMgr, Constants.DATABASE, "Select * from XYZ");
        doThrow(new SQLException()).when(dbMgr.executeQuery(anyString()));
        cmd.run();
    }

}
