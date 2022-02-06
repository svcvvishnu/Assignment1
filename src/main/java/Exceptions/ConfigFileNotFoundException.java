package Exceptions;

public class ConfigFileNotFoundException extends RuntimeException{
    public ConfigFileNotFoundException(String msg) {
        super(msg);
    }
}
