import Exceptions.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

public class DbWrapperIntegrationTest {

    @Before
    public void setup() {
        MySQLDBManager mgr = new MySQLDBManager();
        mgr.reset();
        File f = new File("results.csv");
        f.delete();
    }

    @Test(expected = InvalidArgumentsException.class)
    public void testMainInvalidArg() throws Exception {
        String[] args = new String[]{"A"};
        DbWrapper.main(args);
    }

    @Test
    public void testMainInit() throws Exception {
        String[] args = new String[]{"init", "Book.csv"};
        DbWrapper.main(args);
    }

    @Test(expected = InvalidArgumentsException.class)
    public void testEmptyArgs() throws Exception {
        String[] args = null;
        DbWrapper.main(args);
    }

    @Test(expected = InvalidArgumentsException.class)
    public void testInitInvalidArgs() throws Exception {
        String[] args = new String[]{"init", "abc", "OnlyHeader.csv"};
        DbWrapper.main(args);
    }

    @Test(expected = InvalidArgumentsException.class)
    public void testQueryInvalidArgs() throws Exception {
        String[] args = new String[]{"query", Constants.DATABASE, "abc", "Select * from ONLYHEADER"};;
        DbWrapper.main(args);
    }

    @Test
    public void testInitOnlyHeaderCSV() throws Exception {
        String[] args = new String[]{"init", "OnlyHeader.csv"};
        DbWrapper.main(args);
        args = new String[]{"query", Constants.DATABASE, "Select * from ONLYHEADER"};
        DbWrapper.main(args);
        File f = new File("results.csv");
        Assert.assertTrue(f.exists());
    }

    @Test(expected = InvalidCSVFileException.class)
    public void testInitEmptyCsv() throws Exception {
        String[] args = new String[]{"init", "EmptyCSV.csv"};
        DbWrapper.main(args);
    }

    @Test(expected = InvalidFileException.class)
    public void testInitInvalidFileFormat() throws Exception {
        String[] args = new String[]{"init", "InvalidFileFormat.txt"};
        DbWrapper.main(args);
    }

    @Test(expected = DatabaseNotFoundException.class)
    public void testMainQueryNoDB() throws Exception {
        String[] args = new String[]{"query", Constants.DATABASE, "Select * from BOOK"};
        DbWrapper.main(args);
    }

    @Test
    public void testMainQuery() throws Exception {
        String[] args = new String[]{"init", "Book.csv"};
        DbWrapper.main(args);
        args = new String[]{"query", Constants.DATABASE, "Select * from BOOK"};
        DbWrapper.main(args);
        File f = new File("results.csv");
        Assert.assertTrue(f.exists());
    }

    @Test(expected = InvalidDatabaseNameException.class)
    public void testQueryInvalidDB() throws Exception {
        String[] args = new String[]{"query", "XYZ", "Select * from BOOK"};
        DbWrapper.main(args);
    }

    @Test(expected = DatabaseNotFoundException.class)
    public void testQueryNoInit() throws Exception {
        String[] args = new String[]{"query", Constants.DATABASE, "Select * from BOOK"};
        DbWrapper.main(args);
    }
}
