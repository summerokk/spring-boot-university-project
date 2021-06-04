package com.att.validator.faculty;

import com.att.request.faculty.FacultyAddRequest;
import com.att.validator.faculty.impl.FacultyAddValidatorImpl;
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
class FacultyAddValidatorTest {
    @Test
    void validatorShouldInvokeAllMethods() {
        final FacultyAddRequest request = new FacultyAddRequest("test");

        FacultyAddValidator validator = spy(new FacultyAddValidatorImpl());
        doNothing().when(validator).validateNull(any(Object.class), anyString());
        doNothing().when(validator).validateEmpty(anyString(), anyString());

        validator.validate(request);

        verify(validator, times(1)).validateNull(any(Object.class), anyString());
        verify(validator, times(1)).validateEmpty(anyString(), anyString());
    }
}
