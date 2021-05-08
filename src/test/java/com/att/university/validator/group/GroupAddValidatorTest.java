package com.att.university.validator.group;

import com.att.university.request.course.CourseAddRequest;
import com.att.university.request.group.GroupAddRequest;
import com.att.university.validator.course.CourseAddValidator;
import com.att.university.validator.course.impl.CourseAddValidatorImpl;
import com.att.university.validator.group.impl.GroupAddValidatorImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GroupAddValidatorTest {
    @Test
    void validatorShouldInvokeAllMethods() {
        final GroupAddRequest request = new GroupAddRequest("test", 1);

        GroupAddValidator validator = spy(new GroupAddValidatorImpl());
        doNothing().when(validator).validateNull(any(Object.class), anyString());
        doNothing().when(validator).validateEmpty(anyString(), anyString());

        validator.validate(request);

        verify(validator, times(2)).validateNull(any(Object.class), anyString());
        verify(validator, times(1)).validateEmpty(anyString(), anyString());
    }
}