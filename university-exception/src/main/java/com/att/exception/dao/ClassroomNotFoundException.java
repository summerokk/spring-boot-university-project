package com.att.exception.dao;

public class ClassroomNotFoundException extends RuntimeException {
    public ClassroomNotFoundException(String message) {
        super(message);
    }
}
