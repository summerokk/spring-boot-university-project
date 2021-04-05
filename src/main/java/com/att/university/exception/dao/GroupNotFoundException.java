package com.att.university.exception.dao;

public class GroupNotFoundException extends RuntimeException{
    public GroupNotFoundException(String message) {
        super(message);
    }
}
