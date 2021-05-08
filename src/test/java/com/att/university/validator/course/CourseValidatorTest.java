package com.att.university.validator.course;

import com.att.university.exception.service.ValueIsEmptyException;
import com.att.university.request.course.CourseUpdateRequest;
import com.att.university.validator.course.impl.CourseUpdateValidatorImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CourseValidatorTest {
    private final CourseUpdateValidator updateValidator = new CourseUpdateValidatorImpl();

    @Test
    void courseValidatorShouldThrowExceptionIfNameIsEmpty() {
        CourseUpdateRequest request = new CourseUpdateRequest(1, "");

        Exception exception = assertThrows(ValueIsEmptyException.class,
                () -> updateValidator.validate(request));
        assertEquals("Course name is empty", exception.getMessage());
    }

    @Test
    void courseValidatorShouldNotThrowExceptionIfRequestIsValid() {
        CourseUpdateRequest request = new CourseUpdateRequest(1, "asd");

        assertDoesNotThrow(() -> updateValidator.validate(request));
    }
}
