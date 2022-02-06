import Exceptions.InvalidCSVFileException;
import Exceptions.InvalidFileException;

import java.io.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

public class InitCommand {
    MySQLDBManager dbMgr;
    String fileName;
    private final BufferedReader br;
    public InitCommand(MySQLDBManager dbMgr, String fileLocation) throws FileNotFoundException {
        File f = new File(fileLocation);
        fileName = f.getName();
        if(!fileName.split("\\.")[1].equals("csv")) throw new InvalidFileException("Not a csv file");
        fileName = fileName.split("\\.")[0].toUpperCase(Locale.ROOT);
        br = new BufferedReader(new FileReader(fileLocation));
        this.dbMgr = dbMgr;
    }

    public void run() {
        String line = null;
        try {
            line = br.readLine();
            if(line == null) throw new InvalidCSVFileException("CSV file is Empty");
            dbMgr.createDatabase("Assignment1");
            String cols = line;
            dbMgr.createTable(fileName, List.of(line.split(",")));
            while ((line = br.readLine()) != null) {
                dbMgr.insertRecords(fileName, cols, line);
            }
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
