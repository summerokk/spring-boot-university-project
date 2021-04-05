package com.att.university.exception.service;

public class PasswordTooShortException extends RuntimeException {
    public PasswordTooShortException(String message) {
        super(message);
    }
}
