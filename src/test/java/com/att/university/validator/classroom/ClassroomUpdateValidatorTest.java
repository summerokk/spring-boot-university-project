package com.att.university.validator.classroom;

import com.att.university.request.classroom.ClassroomUpdateRequest;
import com.att.university.validator.classroom.impl.ClassroomUpdateValidatorImpl;
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
class ClassroomUpdateValidatorTest {
    @Test
    void validatorShouldInvokeAllMethods() {
        final ClassroomUpdateRequest request = new ClassroomUpdateRequest(1, 12, 1);

        ClassroomUpdateValidator validator = spy(new ClassroomUpdateValidatorImpl());
        doNothing().when(validator).validateNull(any(Object.class), anyString());

        validator.validate(request);

        verify(validator, times(3)).validateNull(any(Object.class), anyString());
    }
}
