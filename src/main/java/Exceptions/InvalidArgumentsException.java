package Exceptions;

public class InvalidArgumentsException extends RuntimeException{
    public InvalidArgumentsException(String msg) {
        super(msg);
    }
}
