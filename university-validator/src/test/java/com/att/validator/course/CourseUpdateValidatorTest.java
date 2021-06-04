package com.att.validator.course;

import com.att.request.course.CourseUpdateRequest;
import com.att.validator.course.impl.CourseUpdateValidatorImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CourseUpdateValidatorTest {
    @Test
    void validatorShouldInvokeAllMethods() {
        final CourseUpdateRequest updateRequest = new CourseUpdateRequest(1, "test");

        CourseUpdateValidator updateValidator = spy(new CourseUpdateValidatorImpl());
        doNothing().when(updateValidator).validateNull(any(Object.class), anyString());
        doNothing().when(updateValidator).validateEmpty(anyString(), anyString());

        updateValidator.validate(updateRequest);

        verify(updateValidator, times(2)).validateNull(any(Object.class), anyString());
        verify(updateValidator, times(1)).validateEmpty(anyString(), anyString());
    }
}
