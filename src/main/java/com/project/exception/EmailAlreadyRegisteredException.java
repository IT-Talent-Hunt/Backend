package com.project.exception;

public class EmailAlreadyRegisteredException extends Exception {
    public EmailAlreadyRegisteredException(String message) {
        super(message);
    }
}
