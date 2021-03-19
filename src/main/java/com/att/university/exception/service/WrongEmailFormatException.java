package com.att.university.exception.service;

public class WrongEmailFormatException extends RuntimeException {
    public WrongEmailFormatException(String message) {
        super(message);
    }
}
