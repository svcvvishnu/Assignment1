package Exceptions;

public class DatabaseNotFoundException extends RuntimeException{
    public DatabaseNotFoundException(String msg) {
        super(msg);
    }
}
