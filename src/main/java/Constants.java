public interface Constants {
    String INIT = "init";
    String QUERY = "query";

    String DATABASE = "Assignment1";
    String CREATE_TABLE = "CREATE TABLE %s ( ID int NOT NULL AUTO_INCREMENT, %s PRIMARY KEY (ID) );";
    String ADD_COLUMN = "`%s` VARCHAR(255), ";
    String INSERT = "INSERT INTO %s (%s) VALUES (%s);";
}
