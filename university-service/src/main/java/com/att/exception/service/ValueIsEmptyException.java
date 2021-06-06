package com.att.exception.service;

public class ValueIsEmptyException extends RuntimeException {
    public ValueIsEmptyException(String message) {
        super(message);
    }
}
