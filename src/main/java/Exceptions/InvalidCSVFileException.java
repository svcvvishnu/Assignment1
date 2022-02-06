package Exceptions;

public class InvalidCSVFileException extends RuntimeException{
    public InvalidCSVFileException(String msg) {
        super(msg);
    }
}
