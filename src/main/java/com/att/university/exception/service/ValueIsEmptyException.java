package com.att.university.exception.service;

public class ValueIsEmptyException extends RuntimeException {
    public ValueIsEmptyException(String message) {
        super(message);
    }
}
