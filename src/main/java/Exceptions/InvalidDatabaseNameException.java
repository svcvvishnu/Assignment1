package Exceptions;

public class InvalidDatabaseNameException extends RuntimeException{
    public InvalidDatabaseNameException(String msg) {
        super(msg);
    }
}
