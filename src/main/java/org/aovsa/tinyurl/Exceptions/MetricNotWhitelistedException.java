package org.aovsa.tinyurl.Exceptions;

public class MetricNotWhitelistedException extends Exception{
    public MetricNotWhitelistedException(String message) {
        super(message);
    }
    public MetricNotWhitelistedException(String message, Throwable cause) {
        super(message, cause);
    }
}
