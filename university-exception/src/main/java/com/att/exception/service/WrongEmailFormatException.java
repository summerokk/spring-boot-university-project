package com.att.exception.service;

public class WrongEmailFormatException extends RuntimeException {
    public WrongEmailFormatException(String message) {
        super(message);
    }
}
