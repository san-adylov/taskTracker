package com.app.tasktracker.exceptions;

public class BadCredentialException extends RuntimeException{

    public BadCredentialException(String message) {
        super(message);
    }
}
