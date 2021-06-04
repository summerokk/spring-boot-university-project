package com.att.exception.dao;

public class BuildingNotFoundException extends RuntimeException {
    public BuildingNotFoundException(String message) {
        super(message);
    }
}
