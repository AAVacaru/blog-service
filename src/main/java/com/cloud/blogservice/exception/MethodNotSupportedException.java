package com.cloud.blogservice.exception;

public class MethodNotSupportedException extends RuntimeException{

    public MethodNotSupportedException() {}

    public MethodNotSupportedException(String message) {
        super(message);
    }

    public MethodNotSupportedException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
