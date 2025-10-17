package com.skydiveforecast.domain.exception;

public class DropzoneNotFoundException extends RuntimeException {
    
    public DropzoneNotFoundException(String message) {
        super(message);
    }
}