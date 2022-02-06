import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import static org.mockito.Mockito.*;


public class InitCommandUnitTest {
    InitCommand cmd;

    @Mock
    MySQLDBManager dbMgr;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test() throws FileNotFoundException, SQLException {
        cmd = new InitCommand(dbMgr, "src/main/resources/Book.csv");
        doNothing().when(dbMgr).createDatabase(Constants.DATABASE);
        doNothing().when(dbMgr).createTable(anyString(),anyList());
        doNothing().when(dbMgr).insertRecords(anyString(),anyString(), anyString());

        cmd.run();
    }

    @Test(expected = RuntimeException.class)
    public void testCreateDBFail() throws FileNotFoundException, SQLException {
        cmd = new InitCommand(dbMgr, "src/main/resources/Book.csv");
        doThrow(new SQLException()).when(dbMgr).createDatabase(Constants.DATABASE);
        cmd.run();
    }

    @Test(expected = RuntimeException.class)
    public void testCreateTableFail() throws FileNotFoundException, SQLException {
        cmd = new InitCommand(dbMgr, "src/main/resources/Book.csv");
        doNothing().when(dbMgr).createDatabase(Constants.DATABASE);
        doThrow(new SQLException()).when(dbMgr).createDatabase(Constants.DATABASE);
        cmd.run();
    }

    @Test(expected = RuntimeException.class)
    public void testInsertFail() throws FileNotFoundException, SQLException {
        cmd = new InitCommand(dbMgr, "src/main/resources/Book.csv");
        doNothing().when(dbMgr).createDatabase(Constants.DATABASE);
        doThrow(new SQLException()).when(dbMgr).createDatabase(Constants.DATABASE);
        doNothing().when(dbMgr).createTable(anyString(),anyList());
        cmd.run();
    }
}
