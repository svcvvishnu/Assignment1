import Exceptions.DatabaseNotFoundException;
import Exceptions.InvalidArgumentsException;
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

    @Test(expected = DatabaseNotFoundException.class)
    public void testMainQueryNoDB() throws Exception {
        String[] args = new String[]{"query", "Assignment1", "Select * from BOOK"};
        DbWrapper.main(args);
    }

    @Test
    public void testMainQuery() throws Exception {
        String[] args = new String[]{"init", "Book.csv"};
        DbWrapper.main(args);
        args = new String[]{"query", "Assignment1", "Select * from BOOK"};
        DbWrapper.main(args);
        File f = new File("results.csv");
        Assert.assertTrue(f.exists());
    }
}
