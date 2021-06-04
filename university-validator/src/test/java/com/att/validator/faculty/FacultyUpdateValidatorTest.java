package com.att.validator.faculty;

import com.att.request.faculty.FacultyUpdateRequest;
import com.att.validator.faculty.impl.FacultyUpdateValidatorImpl;
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
class FacultyUpdateValidatorTest {
    @Test
    void validatorShouldInvokeAllMethods() {
        final FacultyUpdateRequest updateRequest = new FacultyUpdateRequest(1, "test");

        FacultyUpdateValidator updateValidator = spy(new FacultyUpdateValidatorImpl());
        doNothing().when(updateValidator).validateNull(any(Object.class), anyString());
        doNothing().when(updateValidator).validateEmpty(anyString(), anyString());

        updateValidator.validate(updateRequest);

        verify(updateValidator, times(2)).validateNull(any(Object.class), anyString());
        verify(updateValidator, times(1)).validateEmpty(anyString(), anyString());
    }
}
