import Exceptions.DatabaseNotFoundException;
import Exceptions.InvalidDatabaseNameException;

import java.io.*;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QueryCommand {
    MySQLDBManager dbMgr;
    String query;
    public QueryCommand(MySQLDBManager dbMgr, String database, String query) throws InvalidDatabaseNameException {
        if(!database.equals(Constants.DATABASE)) throw new InvalidDatabaseNameException("Database name should be" + Constants.DATABASE);
        this.query = query;
        this.dbMgr = dbMgr;

        try {
            if(!dbMgr.isDatabaseAvailable()) throw new DatabaseNotFoundException(Constants.DATABASE +" Database is not found");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void run() {
        File csvOutputFile = new File("results.csv");
        try(PrintWriter pw = new PrintWriter(csvOutputFile)) {
            List<String[]> res = dbMgr.executeQuery(query);
            res.stream().map(this::convertToCSV).forEach(pw::println);
        } catch (FileNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String convertToCSV(String[] data) {
        return Stream.of(data).collect(Collectors.joining(","));
    }
}
