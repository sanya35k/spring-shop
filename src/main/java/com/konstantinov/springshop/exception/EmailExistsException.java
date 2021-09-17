package com.konstantinov.springshop.exception;

public class EmailExistsException extends Exception {
    public EmailExistsException() {
        super();
    }
    public EmailExistsException(String message) {
        super(message);
    }
}