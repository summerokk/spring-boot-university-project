package com.att.university.validator.classroom;

import com.att.university.request.classroom.ClassroomAddRequest;
import com.att.university.validator.classroom.impl.ClassroomAddValidatorImpl;
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
class ClassroomAddValidatorTest {
    @Test
    void validatorShouldInvokeAllMethods() {
        final ClassroomAddRequest request = new ClassroomAddRequest(12, 1);

        ClassroomAddValidator validator = spy(new ClassroomAddValidatorImpl());
        doNothing().when(validator).validateNull(any(Object.class), anyString());

        validator.validate(request);

        verify(validator, times(2)).validateNull(any(Object.class), anyString());
    }
}
