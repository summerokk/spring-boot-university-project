package com.att.validator;

import com.att.exception.service.ValueIsEmptyException;

public interface Validator {
    default void validateNull(Object object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
    }

    default void validateEmpty(String value, String message) {
        if (value.trim().isEmpty()) {
            throw new ValueIsEmptyException(message);
        }
    }
}
