package com.att.exception.service;

public class NameIncorrectException extends RuntimeException {
    public NameIncorrectException(String message) {
        super(message);
    }
}
