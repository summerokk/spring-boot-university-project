package com.att.exception.dao;

public class FacultyNotFoundException extends RuntimeException {
    public FacultyNotFoundException(String message) {
        super(message);
    }
}
