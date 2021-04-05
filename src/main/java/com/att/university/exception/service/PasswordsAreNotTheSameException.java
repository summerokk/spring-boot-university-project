package com.att.university.exception.service;

public class PasswordsAreNotTheSameException extends RuntimeException {
    public PasswordsAreNotTheSameException(String message) {
        super(message);
    }
}
