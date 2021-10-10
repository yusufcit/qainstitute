package com.testapp.test;

/**
 * Runtime exception for framework related exceptions
 */
public class FrameworkException extends RuntimeException {

    public FrameworkException(String message) {
        super(message);
    }

    public FrameworkException(Exception e) {
        super(e);
    }

    public FrameworkException(String message, Exception e) { super(message, e); }
}
