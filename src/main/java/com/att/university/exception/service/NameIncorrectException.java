package com.att.university.exception.service;

public class NameIncorrectException extends RuntimeException {
    public NameIncorrectException(String message) {
        super(message);
    }
}
