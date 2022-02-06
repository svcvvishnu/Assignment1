import Exceptions.ConfigFileNotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;


/**
 * DB Manager to connect to MySQL Database
 */
public class MySQLDBManager {
    String url;
    String userName;
    String password;

    public MySQLDBManager() {
        try (InputStream input = MySQLDBManager.class.getClassLoader().getResourceAsStream("config.properties")){
            Properties prop = new Properties();

            if (input == null) {
                throw new ConfigFileNotFoundException("Sorry, unable to find config.properties");
            }
            prop.load(input);
            Class.forName("com.mysql.cj.jdbc.Driver");
            url = prop.getProperty("db.url");
            userName = prop.getProperty("db.user");
            password = prop.getProperty("db.password");
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    public void reset() {
        try {
            Statement s = getConnection().createStatement();
            s.executeUpdate("DROP DATABASE " + Constants.DATABASE);
        } catch (SQLException e) {
            //no-op
        }
    }

    public boolean isDatabaseAvailable() throws SQLException {
        ResultSet rs = getConnection().getMetaData().getCatalogs();
        while (rs.next()) {
            if (rs.getString(1).equals(Constants.DATABASE)) return true;
        }
        return false;
    }
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                String.format("jdbc:mysql://%s:3306/?allowPublicKeyRetrieval=true&useSSL=false", url), userName, password);
    }

    public Connection getDBConnection() throws SQLException {
        return DriverManager.getConnection(
                String.format("jdbc:mysql://%s:3306/%s?allowPublicKeyRetrieval=true&useSSL=false", url, Constants.DATABASE), userName, password);
    }

    public void createDatabase(String dbname) throws SQLException {
        Statement s = getConnection().createStatement();
        s.executeUpdate("DROP DATABASE " + dbname);

        s = getConnection().createStatement();
        s.executeUpdate("CREATE DATABASE " + dbname);
    }

    public void createTable(String tableName, List<String> cols) throws SQLException {
        Statement s = getDBConnection().createStatement();
        String createTableStmt = String.format(Constants.CREATE_TABLE, tableName, constructColNames(cols));
        System.out.println(createTableStmt);
        s.executeUpdate(createTableStmt);
    }

    public void insertRecords(String tableName, String cols, String record) throws SQLException {
        Statement s = getDBConnection().createStatement();
        String values = Arrays.stream(record.split(",")).collect(Collectors.joining("','","'","'"));
        System.out.println(values);
        String insert = String.format(Constants.INSERT, tableName, cols, values);
        System.out.println(insert);
        s.executeUpdate(insert);
    }

    private String constructColNames(List<String> cols) {
        StringBuilder result = new StringBuilder();
        for (String s : cols) {
            result.append(String.format(Constants.ADD_COLUMN, s));
        }
        return result.toString();
    }

    public List<String[]> executeQuery(String query) throws SQLException {
        List<String[]> result = new ArrayList<>();
            Statement s = getDBConnection().createStatement();
            ResultSet rs = s.executeQuery(query);
            boolean colNameAdded = false;
            while (rs.next()) {
                if (!colNameAdded) {
                    result.add(getColsfromResult(rs));
                    colNameAdded = true;
                }
                result.add(getStringFromResult(rs));
            }
        return result;

    }
    private String[] getColsfromResult(ResultSet rs) {
        try {
            ResultSetMetaData md = rs.getMetaData();
            int cols = md.getColumnCount();
            String[] result = new String[cols];
            for (int i=1; i<=cols; i++) {
                result[i-1] = md.getColumnLabel(i);
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String[] getStringFromResult(ResultSet rs) {
        try {
            int cols = rs.getMetaData().getColumnCount();
            String[] result = new String[cols];
            for (int i=1; i<=cols; i++) {
                result[i-1] = rs.getString(i);
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}