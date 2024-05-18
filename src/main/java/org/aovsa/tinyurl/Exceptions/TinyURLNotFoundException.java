package org.aovsa.tinyurl.Exceptions;

public class TinyURLNotFoundException extends Exception{
    public TinyURLNotFoundException(String message) {
        super(message);
    }
    public TinyURLNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
