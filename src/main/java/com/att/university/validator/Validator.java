package com.att.university.validator;

public interface Validator {
    default void validateNull(Object object, String message) {
        if (object == null) {
            throw new RuntimeException(message);
        }
    }
}
